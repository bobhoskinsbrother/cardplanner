package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.model.serializer.Json;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity public class StoryBoardModel extends IdentifiableDeleteableModel<StoryBoardModel>  {

	@QueryKey("hotspotAreas") @OneToMany(cascade=CascadeType.ALL) private List<StoryBoardHotspotAreaModel> hotspotAreas;
	@QueryKey("textAreas") @OneToMany(cascade=CascadeType.ALL) private List<StoryBoardTextAreaModel> textAreas;
	@QueryKey("postIts") @OneToMany(cascade=CascadeType.ALL) private List<PostItModel> postIts;
	@QueryKey("lines") @OneToMany(cascade=CascadeType.ALL) private List<StoryBoardLineModel> lines;
	@QueryKey("width") private Integer width;
	@QueryKey("height") private Integer height;
	@QueryKey("frameType") private String frameType;

	@QueryKey("card") @OneToOne(cascade=CascadeType.PERSIST, mappedBy="storyBoard") private CardModel card;

	public StoryBoardModel() {
		super();
		defaultSetup(null);
	}

    public StoryBoardModel defaultSetup(ObjectCache objectCache) {
		this.setTitle("");
		this.setSortOrder(0);
		this.textAreas = new LinkedList<StoryBoardTextAreaModel>();
		this.hotspotAreas = new LinkedList<StoryBoardHotspotAreaModel>();
		this.lines = new LinkedList<StoryBoardLineModel>();
		this.x = 10;
		this.y = 10;
		this.width = 1260;
        this.height = 748;
        this.frameType = "large_tv";
        return this;
    }

	public void clear(ObjectCache objectCache) {
		destroyIfExists(this.textAreas, objectCache);
		destroyIfExists(this.hotspotAreas, objectCache);
		destroyIfExists(this.lines, objectCache);
		defaultSetup(objectCache);
    }

	private void destroyIfExists(List<? extends IdentifiableDeleteableModel<?>> list, ObjectCache objectCache) {
	    if(list != null && !list.isEmpty()) {
			for (IdentifiableDeleteableModel<?> identifiable : list) {
				objectCache.destroy(identifiable);
            }
		}
    }

	public void drop(CardModel card) {
		Integer x = card.getX();
		Integer y = card.getY();
		for (StoryBoardHotspotAreaModel hotspotArea : hotspotAreas) {
	        if(hotspotArea.containsPoint(x,y)) {
	        	card.setParent(this.card);
	        	card.setStatus(hotspotArea.getStatus());
	        }
        }
    }

	public Integer getWidth() { return width; }
	public Integer getHeight() { return height; }
	public String getFrameType() { return frameType; }

	public List<StoryBoardTextAreaModel> getTextAreas() { return textAreas; }
	public List<StoryBoardHotspotAreaModel> getHotspotAreas() { return hotspotAreas; }
	public List<StoryBoardLineModel> getLines() { return lines; }
	public List<PostItModel> getPostIts() { return postIts; }

	public CardModel getCard() { return this.card; }
	public void setCard(CardModel card) { this.card = card; }

    public String toString() {
    	return new Json<StoryBoardModel>().serialize(this, "people", "facts", "tags");
    }

    public boolean isBlank() {
    	return this.hotspotAreas.isEmpty() && this.lines.isEmpty() && this.textAreas.isEmpty();
    }

    public void addPostIt(PostItModel postIt) {
        postIts.add(postIt);
    }

    public void removePostIt(PostItModel postIt) {
        if(postIts != null && !postIts.isEmpty()) {
            postIts.remove(postIt);
        }
    }

    public void removeHotSpot(ObjectCache objectCache, StoryBoardHotspotAreaModel hotSpot) {
        StatusModel statusToReset = hotSpot.getStatus();
        StatusModel backlog = new StatusService().backlog(objectCache);
        final Set<CardModel> children = card.getChildren();
        if(children!=null) {
            for (CardModel child : children) {
                if(statusToReset.getIdentity().equals(child.getStatus().getIdentity())) {
                    child.setStatus(backlog);
                    objectCache.save(child);
                }
            }
        }
        hotspotAreas.remove(hotSpot);
    }


    public void addLine(int startX, int startY, int endX, int endY) {
        lines.add(makeLine(startX, startY, endX, endY));
    }

    public void addVerticalLine(int x, int endY) {
        lines.add(makeLine(x, 0, x, endY));
    }

    public void addHorizontalLine(int y, int width) {
        lines.add(makeLine(0, y, width, y));
    }

    private StoryBoardLineModel makeLine(int x1, int y1, int x2, int y2) {
        List<StoryBoardLineEndModel> points = Arrays.asList(new StoryBoardLineEndModel(x1, y1), new StoryBoardLineEndModel(x2, y2));
        StoryBoardBrushModel brush = new StoryBoardBrushModel("#000000", 5);
        return new StoryBoardLineModel(brush, points);
    }

    public void setFrameType(String frameType) {
        this.frameType = frameType;
    }

    public void addHotspotArea(StoryBoardHotspotAreaModel hotspotArea) {
        this.hotspotAreas.add(hotspotArea);
    }

    public void addTextArea(StoryBoardTextAreaModel textArea) {
        this.textAreas.add(textArea);
    }
}