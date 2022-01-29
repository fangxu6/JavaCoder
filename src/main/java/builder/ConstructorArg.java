package builder;


public class ConstructorArg {
    private boolean isRef;
    private Class type;
    private Object arg;
    // TODO: 待完善...
    private ConstructorArg(Builder builder){

    }

    public static class Builder {


        private boolean isRef;
        private Class type;
        private Object arg;

        public ConstructorArg builder(){
            return new ConstructorArg(this);
        }

        public boolean isRef() {
            return isRef;
        }

        public void setRef(boolean ref) {
            isRef = ref;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Object getArg() {
            return arg;
        }

        public void setArg(Object arg) {
            this.arg = arg;
        }

    }
}
