public abstract class HydroNumber {
    public static HydroObject proto = HydroObject.fromNative(HydroNumber.class, ObjectProto.proto);
    
    public static HydroObject create(Double number) {
        HydroObject num = new HydroObject(proto, "number");
        
        num.setInnerValue("number", number);
        
        return num;
    }
    
    public static Double toJava(HydroObject number) {
        if(!number.typeof().equals("number")) return null;
        
        return (Double) number.getInnerValue("number");
    }
    
    @ExposeAs("toString")
    public static HydroObject $toString(Evaluator eval, HydroObject self) {
        Double numObject = (Double) self.getInnerValue("number");
        
        if(numObject == null) {
            return HydroString.create("NaN");
        }
        
        double num = numObject;
        
        // remove the .0 from integers
        if(((int) num) == num) {
            String numStr = ((int) num) + "";
            
            return HydroString.create(numStr);
        }
        
        return HydroString.create(num + "");
    }
    
    /**
     * Remove the decimal part of a number
     */
    public static HydroObject floor(Evaluator eval, HydroObject self) {
        Double num = toJava(self);
        
        // NaN remains a NaN
        if(num == null) {
            return create(null);
        }
        
        double floored = (int) num.doubleValue();
        
        return create(floored);
    }
    
    @ExposeAs("+")
    public static HydroObject add(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null) {
            return create(null);
        }
        
        return create(number + number2);
    }
    
    @ExposeAs("-")
    public static HydroObject subtract(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null) {
            return create(null);
        }
        
        return create(number - number2);
    }
    
    @ExposeAs("*")
    public static HydroObject multiply(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null) {
            return create(null);
        }
        
        return create(number * number2);
    }
    
    @ExposeAs("/")
    public static HydroObject divide(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null || number2 == 0) {
            return create(null);
        }
        
        return create(number / number2);
    }
    
    @ExposeAs("%")
    public static HydroObject modulus(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null || number2 == 0) {
            return create(null);
        }
        
        return create(number % number2);
    }
    
    @ExposeAs("==")
    public static HydroObject equal(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        // both NaNs
        if(number == null && number2 == null) {
            return HydroBoolean.create(true);
        }
        
        // one NaN
        if(number == null || number2 == null) {
            return HydroBoolean.create(false);
        }
        
        return HydroBoolean.create(number.equals(number2));
    }
    
    @ExposeAs("<")
    public static HydroObject lessThan(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null) {
            return HydroBoolean.create(false);
        }
        
        return HydroBoolean.create(number < number2);
    }
    
    @ExposeAs(">")
    public static HydroObject greaterThan(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null) {
            return HydroBoolean.create(false);
        }
        
        return HydroBoolean.create(number > number2);
    }
    
    @ExposeAs("<=")
    public static HydroObject lessThanEqual(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null) {
            return HydroBoolean.create(false);
        }
        
        return HydroBoolean.create(number <= number2);
    }
    
    @ExposeAs(">=")
    public static HydroObject greaterThanEqual(Evaluator eval, HydroObject self, HydroObject other) {
        Double number = toJava(self);
        Double number2 = toJava(other);
        
        if(number == null || number2 == null) {
            return HydroBoolean.create(false);
        }
        
        return HydroBoolean.create(number >= number2);
    }
    
    public static HydroObject toChar(Evaluator eval, HydroObject self) {
        Double number = toJava(self);
        
        if(number == null) {
            return HydroString.create("NaN");
        }
        
        return HydroString.create(((char) number.doubleValue()) + "");
    }
}