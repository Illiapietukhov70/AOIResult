package parammetr_classes;

public class Geometry {
    final static char[] xyz = {'X', 'Y', 'Z'};;
    private int[] position;
    private int[] positionLocal;
    private int[] rotation;
    private int[] rotationLocal;
    private int[] size;
    private int[] sizeLocal;

    public Geometry() {
        this.position = new int[] {-1, -1, -1};
        this.positionLocal = new int[] {-1, -1, -1};
        this.rotation = new int[] {-1, -1, -1};
        this.rotationLocal = new int[] {-1, -1, -1};
        this.size= new int[] {-1, -1, -1};
        this.sizeLocal = new int[] {-1, -1, -1};
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public int[] getPositionLocal() {
        return positionLocal;
    }

    public void setPositionLocal(int[] positionLocal) {
        this.positionLocal = positionLocal;
    }

    public int[] getRotation() {
        return rotation;
    }

    public void setRotation(int[] rotation) {
        this.rotation = rotation;
    }

    public int[] getRotationLocal() {
        return rotationLocal;
    }

    public void setRotationLocal(int[] rotationLocal) {
        this.rotationLocal = rotationLocal;
    }

    public int[] getSize() {
        return size;
    }

    public void setSize(int[] size) {
        this.size = size;
    }

    public int[] getSizeLocal() {
        return sizeLocal;
    }

    public void setSizeLocal(int[] sizeLocal) {
        this.sizeLocal = sizeLocal;
    }
}
