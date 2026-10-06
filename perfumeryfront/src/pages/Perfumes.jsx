import React, { useEffect, useMemo, useState } from "react";
import http from "../api/http";
import "../css/perfume.css";

const GENDERS = ["MALE", "FEMALE", "UNISEX"];
const FRAGRANCE_TYPES = ["EDT", "EDP"];

export const Perfumes = () => {
  const [data, setData] = useState([]);
  const [brands, setBrands] = useState([]);
  const [q, setQ] = useState("");
  const [sort, setSort] = useState({ by: "id", dir: "asc" });
  const [newName, setNewName] = useState("");
  const [newGender, setNewGender] = useState("");
  const [newFragranceType, setNewFragranceType] = useState("");
  const [newVolumeMl, setNewVolumeMl] = useState("");
  const [newPrice, setNewPrice] = useState("");
  const [newStockQuantity, setNewStockQuantity] = useState("");
  const [newBrandId, setNewBrandId] = useState("");
  const [editingID, setEditingId] = useState(null);
  const [newImageUrl, setNewImageUrl] = useState("");

  const brandMap = useMemo(() => {
    const m = new Map();
    brands.forEach((b) => m.set(b.id, b.name));
    return m;
  }, [brands]);

  function toggleSort(col) {
    setSort((s) => s.by === col ? { by: col, dir: s.dir === "asc" ? "desc" : "asc" } : { by: col, dir: "asc" });
  }

  useEffect(() => {
    http.get("/perfume").then((res) => setData(Array.isArray(res.data) ? res.data : []));
    http.get("/brand").then((res) => setBrands(Array.isArray(res.data) ? res.data : []));
  }, []);

  const prikazani = useMemo(() => {
    const qq = q.trim().toLowerCase();
    const base = data.map((r) => ({ ...r, brandName: brandMap.get(r.brandId) || "" }));
    const filtered = qq ? base.filter((r) => String(r.name ?? "").toLowerCase().includes(qq) || String(r.gender ?? "").toLowerCase().includes(qq) || String(r.brandName ?? "").toLowerCase().includes(qq)) : base;
    return [...filtered].sort((a, b) => {
      const va = a[sort.by]; const vb = b[sort.by];
      if (va == null) return 1; if (vb == null) return -1;
      if (typeof va === "number") return sort.dir === "asc" ? va - vb : vb - va;
      return sort.dir === "asc" ? String(va).localeCompare(String(vb)) : String(vb).localeCompare(String(va));
    });
  }, [data, q, sort, brandMap]);

  async function handleDelete(id) {
    if (!window.confirm("Delete perfume?")) return;
    await http.delete(`/perfume/${id}`);
    setData((prev) => prev.filter((r) => r.id !== id));
  }

async function handleSubmit(e) {
  e.preventDefault();
  const body = {
    name: newName.trim(),
    gender: newGender,
    fragranceType: newFragranceType,
    volumeMl: parseInt(newVolumeMl) || 0, // dodat fallback ako je prazno
    price: parseFloat(newPrice) || 0,     // dodat fallback ako je prazno
    stockQuantity: newStockQuantity === "" ? 0 : parseInt(newStockQuantity),
    brandId: newBrandId ? Number(newBrandId) : null, // ako nema brenda, salje null umesto 0
    imageUrl: newImageUrl.trim() || null,
  };
  try {
    if (editingID == null) {
      const res = await http.post("/perfume", body);
      setData((prev) => [...prev, res.data]);
    } else {
      const res = await http.put(`/perfume/${editingID}`, { id: editingID, ...body });
      setData((prev) => prev.map((r) => (r.id === editingID ? res.data : r)));
    }
    // RESETOVANJE SVIH POLJA NAKON USPEHA
    setEditingId(null); 
    setNewName(""); 
    setNewGender(""); 
    setNewFragranceType(""); 
    setNewVolumeMl(""); 
    setNewPrice(""); 
    setNewStockQuantity(""); 
    setNewBrandId("");
    setNewImageUrl(""); 
  } catch (e) { 
    alert(e?.response?.data?.message || e.message); 
  }
}


  function startUpdate(row) {
    setEditingId(row.id); setNewName(row.name ?? ""); setNewGender(row.gender ?? "");
    setNewFragranceType(row.fragranceType ?? ""); setNewVolumeMl(row.volumeMl ?? "");
    setNewPrice(row.price ?? ""); setNewStockQuantity(row.stockQuantity ?? ""); setNewBrandId(row.brandId ?? "");
    setNewImageUrl(row.imageUrl ?? "");
  }
  function cancelUpdate() { setEditingId(null); setNewName(""); setNewGender(""); setNewFragranceType(""); setNewVolumeMl(""); setNewPrice(""); setNewStockQuantity(""); setNewBrandId(""); setNewImageUrl("");}

  return (
    <div className="size">
      <header className="admin-head">
        <div><h1>Perfumes</h1><p className="muted">Manage perfume catalogue.</p></div>
        <div className="row-gap">
          <input className="input" placeholder="Search..." value={q} onChange={(e) => setQ(e.target.value)} />
          <button className="btn" onClick={() => setQ("")}>Refresh</button>
        </div>
      </header>

      <form className="panel" onSubmit={handleSubmit} style={{ marginBottom: 12, display: "flex", gap: 8, flexWrap: "wrap", alignItems: "center" }}>
        <input className="input" placeholder="Name" value={newName} onChange={(e) => setNewName(e.target.value)} />
        <select className="input" value={newGender} onChange={(e) => setNewGender(e.target.value)}>
          <option value="">Gender…</option>
          {GENDERS.map((g) => <option key={g} value={g}>{g}</option>)}
        </select>
        <select className="input" value={newFragranceType} onChange={(e) => setNewFragranceType(e.target.value)}>
          <option value="">Type…</option>
          {FRAGRANCE_TYPES.map((t) => <option key={t} value={t}>{t}</option>)}
        </select>
        <input className="input" placeholder="Volume (ml)" value={newVolumeMl} onChange={(e) => setNewVolumeMl(e.target.value)} />
        <input className="input" placeholder="Price (RSD)" value={newPrice} onChange={(e) => setNewPrice(e.target.value)} />
        <input className="input" placeholder="Stock" value={newStockQuantity} onChange={(e) => setNewStockQuantity(e.target.value)} />
        <input
          className="input"
          placeholder="Image URL"
          value={newImageUrl}
          onChange={(e) => setNewImageUrl(e.target.value)}
        />
        <select className="input" value={newBrandId} onChange={(e) => setNewBrandId(e.target.value)}>
          <option value="">Select brand…</option>
          {brands.map((b) => <option key={b.id} value={b.id}>{b.name}</option>)}
        </select>
        <button className="btn primary" type="submit">{editingID == null ? "Save" : "Update"}</button>
        {editingID != null && <button className="btn" type="button" onClick={cancelUpdate}>Cancel</button>}
      </form>

      <table className="table">
        <thead>
          <tr>
            <th onClick={() => toggleSort("id")}>ID</th>
            <th onClick={() => toggleSort("name")}>Name</th>
            <th onClick={() => toggleSort("gender")}>Gender</th>
            <th onClick={() => toggleSort("fragranceType")}>Type</th>
            <th onClick={() => toggleSort("volumeMl")}>Volume</th>
            <th onClick={() => toggleSort("price")}>Price</th>
            <th onClick={() => toggleSort("stockQuantity")}>Stock</th>
            <th onClick={() => toggleSort("brandName")}>Brand</th>
            <th>Image</th>
            <th>Actions</th>
            
          </tr>
        </thead>
        <tbody>
          {prikazani.map((r) => (
            <tr key={r.id}>
              <td>{r.id}</td>
              <td>{r.name}</td>
              <td>{r.gender}</td>
              <td>{r.fragranceType}</td>
              <td>{r.volumeMl} ml</td>
              <td>{Number(r.price).toFixed(2)}</td>
              <td>{r.stockQuantity}</td>
              <td>{brandMap.get(r.brandId) || ""}</td>
              <td>
                {r.imageUrl
                  ? <img src={r.imageUrl} alt={r.name} style={{ width: 50, height: 50, objectFit: "cover", borderRadius: 4 }} />
                  : <span style={{ color: "#aaa", fontSize: "0.8rem" }}>No image</span>
                }
              </td>
              <td style={{ display: "flex", gap: 8 }}>
                <button className="btn" onClick={() => startUpdate(r)}>Edit</button>
                <button className="btn danger" onClick={() => handleDelete(r.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <div className="filler"></div>
    </div>
  );
};

export default Perfumes;