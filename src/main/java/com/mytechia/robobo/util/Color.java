package com.mytechia.robobo.util;

public class Color {

    private int red;

    private int green;

    private int blue;

    private int alpha;
    
    
    
    

    public Color(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Color(int alpha, int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    
    

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.red;
        hash = 41 * hash + this.green;
        hash = 41 * hash + this.blue;
        hash = 41 * hash + this.alpha;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Color other = (Color) obj;
        if (this.red != other.red) {
            return false;
        }
        if (this.green != other.green) {
            return false;
        }
        if (this.blue != other.blue) {
            return false;
        }
        if (this.alpha != other.alpha) {
            return false;
        }
        return true;
    }
    
    

}
