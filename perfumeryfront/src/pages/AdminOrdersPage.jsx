import React, { useEffect, useState } from "react";
import http from "../api/http";
import "../css/admin-orders.css";

const STATUSES = ["CREATED", "PROCESSING", "COMPLETED", "CANCELLED"];

export default function AdminOrdersPage() {
  const [orders, setOrders] = useState([]);
  const [perfumesById, setPerfumesById] = useState({});
  const [loading, setLoading] = useState(true);

  const totalOf = (items) => (items || []).reduce((sum, it) => sum + (it.unitPrice || 0) * (it.quantity || 0), 0);
  const productName = (perfumeId) => { const p = perfumesById[perfumeId]; return p ? p.name : `Perfume #${perfumeId}`; };

  const load = async () => {
    try {
      setLoading(true);
      const [oRes, pRes] = await Promise.all([http.get("/orders"), http.get("/perfume")]);
      setOrders(oRes.data || []);
      const pMap = {};
      (pRes.data || []).forEach((p) => { pMap[p.id] = p; });
      setPerfumesById(pMap);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, []);

  const deleteOrder = async (id) => {
    if (!window.confirm(`Are you sure you want to delete the order with ID #${id}?`)) return;
    await http.delete(`/orders/${id}`);
    setOrders((prev) => prev.filter((o) => o.id !== id));
  };

  const changeStatus = async (id, status) => {
    await http.patch(`/orders/${id}/status?status=${status}`);
    setOrders((prev) => prev.map((o) => (o.id === id ? { ...o, status } : o)));
  };

  const toggleItems = (id) => setOrders((prev) => prev.map((o) => (o.id === id ? { ...o, _open: !o._open } : o)));

  if (loading) return <div style={{ padding: "2rem" }}>Loading...</div>;

  return (
    <div className="admin-wrap">
      <div className="admin-head">
        <h1>Orders</h1>
        <button className="btn-outline" onClick={load}>Refresh</button>
      </div>
      <div className="table-wrap">
        <table className="table">
          <thead>
            <tr>
              <th>ID</th><th>Customer</th><th>Note</th><th>Status</th><th>Items</th><th>Total</th><th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((o) => (
              <React.Fragment key={o.id}>
                <tr>
                  <td>#{o.id}</td>
                  <td>{o.userId ?? "-"}</td>
                  <td>{o.note || "-"}</td>
                  <td>
                    <select value={o.status} onChange={(e) => changeStatus(o.id, e.target.value)} className="select">
                      {STATUSES.map((s) => <option key={s} value={s}>{s}</option>)}
                    </select>
                  </td>
                  <td>{o.items?.length ?? 0}</td>
                  <td>{totalOf(o.items)} RSD</td>
                  <td style={{ display: "flex", gap: 8 }}>
                    <button className="btn" onClick={() => toggleItems(o.id)}>{o._open ? "Hide" : "Items"}</button>
                    <button className="btn danger" onClick={() => deleteOrder(o.id)}>Delete</button>
                  </td>
                </tr>
                {o._open && (
                  <tr>
                    <td colSpan="7">
                      <ul style={{ margin: 0, paddingLeft: "1rem" }}>
                        {(o.items || []).map((it) => (
                          <li key={it.id}>{productName(it.perfumeId)} × {it.quantity} × {it.unitPrice} RSD</li>
                        ))}
                      </ul>
                    </td>
                  </tr>
                )}
              </React.Fragment>
            ))}
          </tbody>
        </table>
      </div>
      <div className="filler"></div>
    </div>
  );
}