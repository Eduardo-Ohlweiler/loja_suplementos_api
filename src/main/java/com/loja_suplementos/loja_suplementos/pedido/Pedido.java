package com.loja_suplementos.loja_suplementos.pedido;

import com.loja_suplementos.loja_suplementos.pedido_item.PedidoItem;
import com.loja_suplementos.loja_suplementos.usuario.Usuario;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(precision = 15, scale = 2)
    private BigDecimal valor_total;

    @Column(precision = 15, scale = 2)
    private BigDecimal quantidade_total;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PedidoItem> pedidoItems;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private PStatus pStatus = PStatus.AGUARDANDO;

    @Column(name = "pagamento_id", length = 50)
    private String pagamentoId;

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public BigDecimal getQuantidade_total() {
        return quantidade_total;
    }

    public void setQuantidade_total(BigDecimal quantidade_total) {
        this.quantidade_total = quantidade_total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<PedidoItem> getPedidoItems() {
        return pedidoItems;
    }

    public void setPedidoItems(List<PedidoItem> pedidoItems) {
        this.pedidoItems = pedidoItems;
    }

    public PStatus getpStatus() {
        return pStatus;
    }

    public void setpStatus(PStatus pStatus) {
        this.pStatus = pStatus;
    }

    public String getPagamento_id() {
        return pagamentoId;
    }

    public void setPagamento_id(String pagamento_id) {
        this.pagamentoId = pagamento_id;
    }
}
