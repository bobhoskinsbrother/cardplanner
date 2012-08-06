package uk.co.itstherules.cardplanner.model;

import java.util.LinkedHashSet;
import java.util.Set;

import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class TagModelGenerator {

	public Set<TagModel> make(ObjectCache cache, Set<String> tags) {
		Set<TagModel> tagsList = new LinkedHashSet<TagModel>();
		for (String tag : tags) {
			tag = tag.trim();
			if (!"".equals(tag)) {
				tagsList.add(make(cache, tag));
			}
		}
		return tagsList;
	}

	public TagModel make(ObjectCache cache, String tagName) {
		tagName = tagName.trim();
		if (!"".equals(tagName)) {
			TagModel retrievedTag = cache.retrieve(TagModel.class, "title", tagName);
			if(retrievedTag!=null) return retrievedTag;
			return new TagModel(tagName);
		}
		throw new IllegalArgumentException("Empty Tags are not allowed");
	}

}
