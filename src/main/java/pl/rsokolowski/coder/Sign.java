package pl.rsokolowski.coder;

import java.util.Objects;

public class Sign {

    private final String ink;
    private final int quantity;

    public Sign(String ink, int quantity) {
        this.ink = ink;
        this.quantity = quantity;
    }

    public String getInk() {
        return ink;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sign sign1 = (Sign) o;
        return quantity == sign1.quantity &&
                ink.equals(sign1.ink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ink, quantity);
    }

    @Override
    public String toString() {
        return "(" + ink + ", " + quantity + ")";
    }

}
