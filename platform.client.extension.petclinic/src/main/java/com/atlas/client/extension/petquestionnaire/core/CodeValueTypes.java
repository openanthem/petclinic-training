package com.atlas.client.extension.petquestionnaire.core;

import java.util.ArrayList;
import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Model.Param.Values.Source;
import com.antheminc.oss.nimbus.domain.model.config.ParamValue;
/**
 * @author Andrew Jo
 *
 */
public class CodeValueTypes {
	
	public static class YNType implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Yes", "Yes"));
			values.add(new ParamValue("No", "No"));
			return values;
		}
	}
	
    public static class InboundType implements Source {
    	@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Internet Search", "Internet Search"));
            values.add(new ParamValue("Blog Post", "Blog Post"));
			values.add(new ParamValue("Colleague", "Colleague"));
			values.add(new ParamValue("Friend", "Friend"));
			values.add(new ParamValue("Other", "Other"));
			return values;
		}
    }
    
    public static class InboundReason implements Source {
    	@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Good Recommendations", "Good Recommendations"));
            values.add(new ParamValue("Closest location to you", "Closest Location to you"));
			values.add(new ParamValue("Most well known to you", "Most well known to you"));
			values.add(new ParamValue("Best Advertised", "Best Advertised"));
			values.add(new ParamValue("Other", "Other"));
			return values;
		}
    }
    
    public static class VisitReason implements Source {
    	@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Routine vaccination appointment/checkup", "Routine vaccination appointment/checkup"));
            values.add(new ParamValue("Medical problem/illness appointment", "Medical problem/illness appointment"));
			values.add(new ParamValue("Emergency visit", "Emergency visit"));
			values.add(new ParamValue("Grooming", "Grooming"));
			values.add(new ParamValue("Boarding", "Boarding"));
			values.add(new ParamValue("To purchase food or medications", "To purchase food or medications"));
			values.add(new ParamValue("Other", "Other"));
			return values;
		}
    }
    
	public static class SatisfactionType implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("N/A", "N/A"));
			values.add(new ParamValue("Excellent", "Excellent"));
			values.add(new ParamValue("Good", "Good"));
			values.add(new ParamValue("Fair", "Fair"));
			values.add(new ParamValue("Poor", "Poor"));
			return values;
		}
	}
	
	public static class PositiveSatisfactionType implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Excellent", "Excellent"));
			values.add(new ParamValue("Good", "Good"));
			return values;
		}
	}
	
	public static class YesTest implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Yes", "Yes"));
			values.add(new ParamValue("No", "No"));
			values.add(new ParamValue("MayBe", "MayBe"));
			return values;
		}
	}
	public static class NoTest implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("No", "No"));
			values.add(new ParamValue("No", "No"));
			values.add(new ParamValue("No", "No"));
			values.add(new ParamValue("No", "No"));
			return values;
		}
	}
	public static class NeitherTest implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Neither Yes nor No", "Neither Yes nor No"));
			values.add(new ParamValue("Neither Yes nor No", "Neither Yes nor No"));
			return values;
		}
	}
	
	public static class AllTest implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Yes", "Yes"));
			values.add(new ParamValue("No", "No"));
			return values;
		}
	}
	
	public static class timeSelection implements Source {
		@Override
		public List<ParamValue> getValues(String paramPath) {
			List<ParamValue> values = new ArrayList<>();
			values.add(new ParamValue("Date Only", "Date Only"));
			values.add(new ParamValue("Time Only", "Time Only"));
			values.add(new ParamValue("Both Date and Time", "Both Date and Time"));
			return values;
		}
	}
	
 

}
