package com.tadamia;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "triangle")
@XmlType(propOrder = { "a", "b", "c" })
public class Triangle implements Comparable {
    double a,b,c;
    public Triangle(){}

    public Triangle(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double getLength() {
        return this.a+this.b+this.c;
    }

    public double getA() {
        return a;
    }
    @XmlElement
    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }
    @XmlElement
    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }
    @XmlElement
    public void setC(double c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "a " + this.a +" b " + this.b + " c " + this.c;
    }

    @Override
    public int compareTo(Object o) {
        if (o == null || this.getClass() != o.getClass())
            return -1;

        Triangle triangle = (Triangle) o;
        return this.equals(triangle) ? 0 : this.largestArea(triangle);
    }

    private int largestArea(Triangle t) {
        return this.getLength() > t.getLength() ? 1 : -1;
    }
}
