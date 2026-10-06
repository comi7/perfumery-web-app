import React, { useMemo, useState } from "react";
import http from "../api/http";
import "../css/shop.css";

const CartPage = ({ cart, setCart, userId }) => {
  const [submitting, setSubmitting] = useState(false);
  // stanje za napomenu kupca
  const [customerNote, setCustomerNote] = useState("");

  const updateQuantity = (cartId, newQty) => {
    const q = Math.max(1, Math.floor(Number(newQty) || 1));
    setCart(cart.map((c) => (c.cartId === cartId ? { ...c, quantity: q } : c)));
  };

  const removeItem = (cartId) => setCart(cart.filter((c) => c.cartId !== cartId));

  const total = useMemo(() => cart.reduce((sum, c) => sum + (Number(c.price) || 0) * (Number(c.quantity) || 1), 0), [cart]);

  const createOrder = async () => {
    if (!userId) { alert("Prijavite se da biste završili kupovinu."); return; }
    if (!cart.length) return;
    
    const dto = {
      status: "CREATED",
      // ovde saljem tekst koji je kupac uneo (ako je prazno, salje podrazumevani tekst)
      note: customerNote.trim() || "Nema napomene.",
      userId,
      items: cart.map((c) => ({ perfumeId: c.id, quantity: c.quantity || 1 })),
    };
    
    try {
      setSubmitting(true);
      await http.post("/orders", dto);
      alert("Porudžbina kreirana!");
      setCart([]);
      setCustomerNote(""); // reset polja za napomenu nakon uspesne porudzbine
    } catch (err) {
      alert("Neuspešno kreiranje porudžbine.");
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div>
      <div className="cart-wrap">
        <h2>My cart</h2>
        {cart.length === 0 ? <p>🛒Oops! Cart is empty. Go to Brands to choose products to add.</p> : (
          <ul>
            {cart.map((c) => (
              <li key={c.cartId}>
                <span>{c.name} – {Number(c.price) || 0} RSD</span>
                <div className="qty-controls">
                  <p>Quantity</p>
                  <button type="button" onClick={() => updateQuantity(c.cartId, (c.quantity || 1) - 1)} disabled={submitting}>–</button>
                  <input type="number" min="1" value={c.quantity || 1} onChange={(e) => updateQuantity(c.cartId, parseInt(e.target.value, 10))} />
                  <button type="button" onClick={() => updateQuantity(c.cartId, (c.quantity || 1) + 1)} disabled={submitting}>+</button>
                </div>
                <button type="button" onClick={() => removeItem(c.cartId)} className="btn-delete" disabled={submitting}>Delete</button>
              </li>
            ))}
          </ul>
        )}
        
        {cart.length > 0 && (
          <>
            
            <div className="cart-note-section">
              <label htmlFor="order-note">Napomena uz porudžbinu (opciono):</label>
              <textarea
                id="order-note"
                value={customerNote}
                onChange={(e) => setCustomerNote(e.target.value)}
                placeholder="Unesite dodatne zahteve ili informacije za isporuku..."
                disabled={submitting}
                rows={3}
                style={{ width: "100%", marginTop: "5px", padding: "8px", resize: "vertical" }}
              />
            </div>

            <div className="cart-total">Total: {total} RSD</div>
            <button onClick={createOrder} className="btn-order" disabled={submitting}>{submitting ? "Processing..." : "Place order"}</button>
          </>
        )}
      </div>
      <div className="filler"></div>
    </div>
  );
};

export default CartPage;
