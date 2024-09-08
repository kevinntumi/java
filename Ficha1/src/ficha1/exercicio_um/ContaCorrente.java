/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ficha1.exercicio_um;

/**
 *
 * @author Kevin Ntumi
 */
public class ContaCorrente {
    private String uiClassID;
    private boolean estado;
    private double saldo;

    public double getTaxaOperacao() {
        if (getUiClassID().equals("ContaCorrenteEspecial")) return 0.5;
        return 0.1;
    }

    public double getSaldo() {
        return saldo;
    }

    protected void setUiClassID(String uiClassID) {
        if (!uiClassID.equals("ContaCorrenteEspecial")) return;
        this.uiClassID = uiClassID;
    }

    protected String getUiClassID() {
        if ("ContaCorrenteEspecial".equals(uiClassID)) return uiClassID;
        return "ContaCorrente";
    }

    public boolean getEstado() {
        return estado;
    }

    public void modificarEstadoDaConta(boolean estado) {
        if (estado == getEstado()) return;
        this.estado = estado;
    }
    
    public String depositar(int valorADepositar) {
        if (getEstado() == EstadoConta.DESACTIVADA) return "Apenas as contas activas podem efectuar depositos!";
        saldo += valorADepositar;
        return "Deposito efectuado com sucesso";
    }
    
    public String levantarQuantia(int quantiaALevantar) {
        if (getEstado() == EstadoConta.DESACTIVADA) return "Apenas as contas activas podem efectuar levantamentos!";
        if (quantiaALevantar < 0) return "A quantia a levantar deve ser maior que 0!";
        if (quantiaALevantar > saldo) return "A quantia a levantar nao deve ser maior que o saldo. O valor maximo permitido para o levantamento eh: " + (getTaxaOperacao() * saldo);
        saldo -= (quantiaALevantar + (getTaxaOperacao() * quantiaALevantar));
        return "Levantamento efectuado com sucesso";
    }
    
    public double obterSaldoAtual() {
        return saldo;
    }

    @Override
    public String toString() {
        return getUiClassID() + "{" + "taxaOperacao=" + getTaxaOperacao() + ", estado=" + estado + ", saldo=" + saldo + '}';
    }
}