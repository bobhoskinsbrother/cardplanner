package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.date.DateBuilder;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;

public final class BurnUpLineModel {

    private final StatusModel status;
    private final Date from;
    private final Date to;
    private List<BurnUpDayModel> line;
    private double highestValue;

    public BurnUpLineModel(StatusModel status, Date from, Date to, Set<LogModel> logs) {
        this.status = status;
        this.from = scrubDateTimeToDate(from);
        this.to = scrubDateTimeToDate(to);
        generateLineFrom(logs);
    }

    public StatusModel getStatus() { return status; }
    public Date getFrom() { return from; }
    public Date getTo() { return to; }
    public double getHighestValue() { return highestValue; }
    public List<BurnUpDayModel> getLine() { return line; }

    private Date scrubDateTimeToDate(Date date) {
        return new DateBuilder(date).build();
    }

    private void generateLineFrom(Set<LogModel> logs) {
        Map<String, BurnUpDayModel> map = new HashMap<String, BurnUpDayModel>();
        double runningTotal = 0.0;
        for (LogModel log : logs) {
            Date date = scrubDateTimeToDate(log.getDate());
            BurnUpDayModel current = createDayModelIfNotExists(map, date);
            final double currentTotal = log.getCard().calculateEffort();
            if(isBurnUp(log)) {
                current.add(currentTotal);
                runningTotal += currentTotal;
            } else if(isBurnDown(log)) {
                current.subtract(currentTotal);
                runningTotal -= currentTotal;
            }
        }
        this.line = generateListAndSortByDate(map);
        this.highestValue = runningTotal;
    }

    private List<BurnUpDayModel> generateListAndSortByDate(Map<String, BurnUpDayModel> map) {
        final List<BurnUpDayModel> list = new LinkedList<BurnUpDayModel>(map.values());
        Collections.sort(list, new Comparator<BurnUpDayModel>() {
            public int compare(BurnUpDayModel one, BurnUpDayModel two) {
                return one.getDate().compareTo(two.getDate());
            }
        });
        return list;
    }

    private BurnUpDayModel createDayModelIfNotExists(Map<String, BurnUpDayModel> map, Date date) {
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
        BurnUpDayModel current = map.get(dateString);
        if(current == null) {
            map.put(dateString, new BurnUpDayModel(date));
            current = map.get(dateString);
        }
        return current;
    }

    private boolean isBurnDown(LogModel log) {
        return matchesStatus(log.getFromStatus());
    }

    private boolean isBurnUp(LogModel log) {
        return matchesStatus(log.getToStatus());
    }

    private boolean matchesStatus(StatusModel status) {
        return this.status.getIdentity().equals(status.getIdentity());
    }


}