import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

public class EmisorCelda extends Thread {

    private int x;
    private int y;
    private Buffer buffer;
    private ArrayList<Celda> vecinos;
    private EstadoCelda estado;

    public EmisorCelda(int x, int y, Buffer buffer, ArrayList<Celda> vecino, EstadoCelda estado) {
        this.x = x;
        this.y = y;
        this.buffer = buffer;
        this.vecinos = vecino;
        this.estado = estado;
    }

    @Override
    public void run() {
        while (App.iteraciones > 0) {
            for (Celda vecino : this.getVecinos()) {
                vecino.getBuffer().almacenar(this.getEstado().getEstado());
            }
            try {
                App.getBarreraVerificacion().await();
                App.getBarreraIteracion().await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }



    // Getters and Setters
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

    public EstadoCelda getEstado() {
        return estado;
    }

    public void setEstado(EstadoCelda estado) {
        this.estado = estado;
    }
}
