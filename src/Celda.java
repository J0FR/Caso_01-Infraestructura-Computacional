import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

public class Celda {

    private int id;
    private int x;
    private int y;
    private EstadoCelda estado;
    private Buffer buffer;
    private ArrayList<Celda> vecinos;
    private SubscriptorCelda subscriptorCelda;
    private EmisorCelda emisorCelda;

    public Celda(int id, int x, int y, boolean estado) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.estado = new EstadoCelda(estado);
        this.buffer = new Buffer(x + 1);
        this.vecinos = new ArrayList<Celda>();
        this.subscriptorCelda = new SubscriptorCelda(x, y, buffer, vecinos, this.estado);
        this.emisorCelda = new EmisorCelda(x, y, buffer, vecinos, this.estado);
    }

    // Funciones auxiliares
    public void asignarVecinos() {
        int[] dirs = {-1, 0, 1};
        for (int dx : dirs) {
            for (int dy : dirs) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < App.matriz.length && ny >= 0 && ny < App.matriz[0].length) {
                    this.vecinos.add(App.matriz[nx][ny]);
                }
            }
        }
    }

    public void run() {
        this.subscriptorCelda.start();
        this.emisorCelda.start();
    }



    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public EstadoCelda getEstado() {
        return estado;
    }

    public void setEstado(EstadoCelda estado) {
        this.estado = estado;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public ArrayList<Celda> getVecinos() {
        return vecinos;
    }

    public void setVecinos(ArrayList<Celda> vecinos) {
        this.vecinos = vecinos;
    }

    public SubscriptorCelda getSubscriptorCelda() {
        return subscriptorCelda;
    }

    public void setSubscriptorCelda(SubscriptorCelda subscriptorCelda) {
        this.subscriptorCelda = subscriptorCelda;
    }

    public EmisorCelda getEmisorCelda() {
        return emisorCelda;
    }

    public void setEmisorCelda(EmisorCelda emisorCelda) {
        this.emisorCelda = emisorCelda;
    }
}
