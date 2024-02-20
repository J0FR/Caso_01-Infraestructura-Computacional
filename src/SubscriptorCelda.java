import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;

public class SubscriptorCelda extends Thread {

    private int x;
    private int y;
    private Buffer buffer;
    private ArrayList<Celda> vecinos;
    private EstadoCelda estado;

    public SubscriptorCelda(int x, int y, Buffer buffer, ArrayList<Celda> vecinos, EstadoCelda estado) {
        this.x = x;
        this.y = y;
        this.buffer = buffer;
        this.vecinos = vecinos;
        this.estado = estado;
    }

    @Override
    public void run() {
        while (App.iteraciones > 0) {
            int notificacionesRecibidas = 0;
            int contadorVivos = 0;
            while (notificacionesRecibidas < vecinos.size()) {
                while (buffer.getN() == 0) {
                    Thread.yield();
                }
                boolean value = buffer.retirar();
                if (value) {
                    contadorVivos++;
                }
                notificacionesRecibidas++;
            }
            try {
                App.getBarreraVerificacion().await();
                this.actualizarEstado(contadorVivos);
                App.getBarreraIteracion().await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }



    // Funciones auxiliares
    private void actualizarEstado(int vivos) {
        if (!this.getEstado().getEstado() && vivos == 3) {
            this.getEstado().setEstado(true);
        }
        // Muere: una celda viva puede morir por uno de 2 casos: 
        // Sobrepoblación: si tiene más de tres celdas vivas alrededor o 
        // Aislamiento: si no tiene celdas vivas alrededor. 
        else if (this.getEstado().getEstado() && (vivos < 1 || vivos > 3)) {
            this.getEstado().setEstado(false);
        }
        // Vive: una celda se mantiene viva si tiene entre 1 y 3 celdas vecinas vivas a su alrededor. 
        else if (this.getEstado().getEstado() && (vivos >= 1 || vivos <= 3)) {
            this.getEstado().setEstado(true);
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
