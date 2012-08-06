package uk.co.itstherules.yawf.inbound.binders;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import uk.co.itstherules.yawf.inbound.annotations.processor.BaseValuesProviderBinder;
import uk.co.itstherules.yawf.inbound.binders.objectproviders.ImplementationProviderRegister;

public class BinderRegister {
	
	private List<QueryValueBinder> binders;

	public BinderRegister(BaseValuesProviderBinder binder, ImplementationProviderRegister register, QueryValueBinder... moreBinders) {
		this.binders = new ArrayList<QueryValueBinder>();
		this.binders.addAll(Arrays.asList(moreBinders));
		this.binders.add(new StringBinder(binder, register));
		this.binders.add(new IntegerBinder(binder, register));
		this.binders.add(new BooleanBinder(binder, register));
		this.binders.add(new LongBinder(binder, register));
		this.binders.add(new DoubleBinder(binder, register));
		this.binders.add(new DateBinder(binder, register));
		this.binders.add(new EnumBinder(binder, register));
		this.binders.add(new ListOfStringBinder(binder, register));
		this.binders.add(new SetBinder(this, binder, register));
		this.binders.add(new ListBinder(this, binder, register));
		this.binders.add(new MapOfStringsBinder(binder, register));
		this.binders.add(new ClassBinder(binder, register));
    }

	public void register(QueryValueBinder converter) {
		this.binders.add(0, converter);
    }
	
	public QueryValueBinder retrieve(Class<?> fieldClass, Type genericType) throws NoBinderFoundException {
		for (QueryValueBinder converter : this.binders) {
	        if(converter.canHandle(fieldClass, genericType)) {
	        	return converter;
	        }
        }
		throw new NoBinderFoundException("There is no Basic Converter defined for " + genericType.getClass().getName());	
	}
	
}
