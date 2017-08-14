package mcmu.containers.typed_structures;

import mcmu.containers.Sided;

import javax.lang.model.type.NullType;
import java.util.ArrayList;

public class TypedData<D> {
    D value;
    transient DataType dt;
    public TypedData(D val, DataType type) {
        this.value = val;
        this.dt = type;
    }
    public TypedData(DataType type) {
        this.dt = type;
    }
    public void setValue(D value) {
        this.value = value;
    }

    public D getValue() {
        return value;
    }

    public DataType getDt() {
        return dt;
    }

    public String toString() {
        return value.toString();
    }
    public static TypedData<String> string() {
        return new TypedData<>("", DataType.STRING);
    }
    public static TypedData<String> string(String d) {
        return new TypedData<>(d, DataType.STRING);
    }
    public static TypedData<ArrayList<String>> StringList() {
        return new TypedData<>(new ArrayList<>(), DataType.LIST);
    }
    public static TypedData<Boolean> bool(boolean d) {
        return new TypedData<>(d, DataType.BOOL);
    }
    public static TypedData<NullType> none() {
        return new TypedData<>(DataType.NONE);
    }
    public static TypedData<Enum<?>> Enum(Enum<?> d) {
        return new TypedData<>(d, DataType.ENUM);
    }
}
