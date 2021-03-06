package uk.co.itstherules.cardplanner.view.active;

import org.apache.poi.hssf.usermodel.*;
import uk.co.itstherules.cardplanner.model.CardModel;
import uk.co.itstherules.cardplanner.model.PersonModel;
import uk.co.itstherules.cardplanner.model.TagModel;
import uk.co.itstherules.yawf.controller.ContentType;
import uk.co.itstherules.yawf.inbound.ValuesProvider;
import uk.co.itstherules.yawf.inbound.annotations.processor.QueryKeyViolations;
import uk.co.itstherules.yawf.model.Entity;
import uk.co.itstherules.yawf.model.ObjectState;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;
import uk.co.itstherules.yawf.modelview.ModelView;
import uk.co.itstherules.yawf.view.context.ViewContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;


public class ExcelExportXls implements ModelView {

	public String asText(ObjectCache objectCache, ValuesProvider valuesProvider, ViewContext mixInContext, QueryKeyViolations violations) {
	    throw new RuntimeException("TODO: Not yet implemented");
    }

    public void renderTo(ObjectCache objectCache, ValuesProvider valuesProvider, HttpServletResponse response, ViewContext mixInContext, QueryKeyViolations violations) {
		Set<CardModel> cards = objectCache.all(CardModel.class, ObjectState.Active);

		HSSFWorkbook workBook = new HSSFWorkbook();
	    HSSFSheet cardsSheet = workBook.createSheet("Cards");
	    HSSFCellStyle cellStyle = workBook.createCellStyle();
	    HSSFDataFormat dataFormat = workBook.createDataFormat();
	    short format = dataFormat.getFormat("dd/MM/yyyy hh:mm");
	    cellStyle.setDataFormat(format);

	    int count = 0;
	    
	    for (CardModel card : cards) {
	        HSSFRow row = cardsSheet.createRow(count);
	        row.createCell(0).setCellValue(new HSSFRichTextString(card.getIdentity()));
	        row.createCell(1).setCellValue(new HSSFRichTextString(card.getType().getTitle()));
	        row.createCell(2).setCellValue(new HSSFRichTextString(title(card.getParent())));
	        row.createCell(3).setCellValue(new HSSFRichTextString(card.getTitle()));
	        row.createCell(4).setCellValue(new HSSFRichTextString(card.getBody()));
	        row.createCell(5).setCellValue(card.getSortOrder());
	        row.createCell(6).setCellValue(new HSSFRichTextString(card.getEffort().getType().getTitle()));
	        row.createCell(7).setCellValue(card.getEffort().getType().asRate(card.getEffort().getAmount()));
	        row.createCell(8).setCellValue(new HSSFRichTextString(card.getValue().getType().getTitle()));
	        row.createCell(9).setCellValue(card.getValue().getType().asRate(card.getValue().getAmount()));
	        row.createCell(10).setCellValue(new HSSFRichTextString(printP(card.getPeople())));
	        row.createCell(11).setCellValue(new HSSFRichTextString(title(card.getStatus())));
	        row.createCell(12).setCellValue(new HSSFRichTextString(printT(card.getTags())));
	        HSSFCell cell13 = row.createCell(13);
			cell13.setCellStyle(cellStyle);
			cell13.setCellValue(card.getUpdated());
	        count++;
        }
        try {
        	ServletOutputStream outputStream;
	        outputStream = response.getOutputStream();
	        response.setContentType(ContentType.xls.name());
	        workBook.write(outputStream);
	        outputStream.close();
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }
	}
	
	private String title(Entity<?> entity) {
		if(entity!=null) return entity.getTitle();
		return "";
    }

	private String printT(Set<TagModel> tags) {
		StringBuffer buffer = new StringBuffer();
		for (TagModel object : tags) {
			buffer.append(object.getTitle());
			buffer.append(",");
		}
		if(tags.size() > 0) {
			return buffer.substring(0, buffer.length()-1);
		}
		return buffer.toString();
    }

	public String printP(Set<PersonModel> set) {
		StringBuffer buffer = new StringBuffer();
		for (PersonModel object : set) {
			buffer.append(object.getTitle());
			buffer.append(" (");
			buffer.append(object.getInitials());
			buffer.append(")");
			buffer.append(",");
		}
		if(set.size() > 0) {
			return buffer.substring(0, buffer.length()-1);
		}
		return buffer.toString();
	}

	public String getKey() {
	    return "Xls";
    }

	public String getViewTitle() { return ""; }
}
