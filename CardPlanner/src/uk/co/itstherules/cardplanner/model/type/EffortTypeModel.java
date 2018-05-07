package uk.co.itstherules.cardplanner.model.type;

import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.ValidateWithMethod;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.IdentifiableDeleteableModel;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public final class EffortTypeModel extends IdentifiableDeleteableModel<EffortTypeModel> implements TypeModel {

    @QueryKey("rate") @NotNull @NotNegative private double rate;
    @Enumerated(EnumType.STRING) @QueryKey("type") @NotNull private TypeType type;
    @QueryKey("conversionString") @ValidateWithMethod(methodName = "conversionStringValidation", parameterType = String.class) private String conversionString;

    public EffortTypeModel() {
        super();
        this.setTitle("");
        this.rate = 1.0;
        this.conversionString = "";
        this.type = TypeType.NumericBased;
    }

    public void setConversionString(String conversionString) { this.conversionString = conversionString; }

    public void setRate(double rate) { this.rate = rate; }

    public void setType(TypeType type) { this.type = type; }

    public double getRate() { return rate; }

    public TypeType getType() { return type; }

    public String getConversionString() { return this.conversionString; }

    public double asRate(double amount) {
        if (type == TypeType.NumericBased) {
            try {
                return amount * getRate();
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        if (type == TypeType.TextBased) {
            StringTokenizer tokenizer = new StringTokenizer(conversionString, "=,", false);
            while (tokenizer.hasMoreElements()) {
                String key = tokenizer.nextToken();
                String value = tokenizer.nextToken();
                if (key != null) {
                    key = key.trim();
                    value = value.trim();
                    if (key.equals(amount)) {
                        return (amount * getRate());
                    }
                }
            }
        }
        throw new RuntimeException(amount + " not recognised");
    }

    boolean conversionStringValidation(String string) {
        if (string == null || "".equals(string)) {
            return true;
        }
        StringTokenizer tokenizer = new StringTokenizer(string, "=,", false);
        if (!tokenizer.hasMoreElements()) {
            return false;
        }
        while (tokenizer.hasMoreElements()) {
            try {
                Pattern pattern = Pattern.compile("[\\w]*");
                Matcher matcher = pattern.matcher(tokenizer.nextToken().trim());
                if (!matcher.find()) {
                    return false;
                }
                Integer.parseInt(tokenizer.nextToken().trim());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public EffortTypeModel defaultSetup(ObjectCache objectCache) {
        return this;
    }

}