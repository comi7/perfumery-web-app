import React, { useEffect, useState } from 'react'
import http from '../api/http';
import TableRowBrand from '../components/TableRowBrand';
import '../css/brand.css'

export const Brands = () => {
  const [data, setData] = useState([]);
  const [q, setQ] = useState("");
  const [sort, setSort] = useState({ by: "id", dir: "asc" });
  const [newName, setNewName] = useState("");
  const [newCountry, setNewCountry] = useState("");
  const [editingID, setEditingId] = useState(null);
  const [newImageUrl, setNewImageUrl] = useState("");

  function toggleSort(col) {
    setSort((s) => s.by === col ? { by: col, dir: s.dir === "asc" ? "desc" : "asc" } : { by: col, dir: "asc" });
  }

  useEffect(() => {
    http.get("/brand").then((res) => setData(Array.isArray(res.data) ? res.data : []));
  }, []);

  let prikazani = data.filter((r) =>
    (r.name || "").toLowerCase().includes(q.toLowerCase()) ||
    (r.country || "").toLowerCase().includes(q.toLowerCase())
  );

  prikazani.sort((a, b) => {
    const va = a[sort.by]; const vb = b[sort.by];
    if (va == null) return 1; if (vb == null) return -1;
    if (typeof va === "number") return sort.dir === "asc" ? va - vb : vb - va;
    return sort.dir === "asc" ? String(va).localeCompare(String(vb)) : String(vb).localeCompare(String(va));
  });

  async function handleDelete(id) {
    if (!window.confirm("Delete brand?")) return;
    await http.delete(`/brand/${id}`);
    setData((prev) => prev.filter((r) => r.id !== id));
  }

  async function handleSubmit(e) {
    e.preventDefault();
    if (!newName.trim()) { alert("Type in brand name"); return; }
    try {
      if (editingID == null) {
        const res = await http.post("/brand", {
          name: newName.trim(),
          country: newCountry.trim() || null,
          imageUrl: newImageUrl.trim() || null,
        });
        setData((prev) => [...prev, res.data]);
      } else {
        
        const res = await http.put(`/brand/${editingID}`, {
          id: Number(editingID),
          name: newName.trim(),
          country: newCountry.trim() || null,
          imageUrl: newImageUrl.trim() || null,
        });
        setData((prev) => prev.map((r) => (r.id === editingID ? res.data : r)));
      }

      setEditingId(null); 
      setNewName(""); 
      setNewCountry("");
      setNewImageUrl(""); 
    } catch (e) { alert(e?.response?.data?.message || e.message); }
  }

  function startUpdate(row) { setEditingId(row.id); setNewName(row.name ?? ""); setNewCountry(row.country ?? ""); setNewImageUrl(row.imageUrl ?? "");}
  function cancelUpdate() { setEditingId(null); setNewName(""); setNewCountry(""); setNewImageUrl("");}

  return (
    <div className="size">
      <header className="admin-head">
        <div><h1>Brands</h1><p className="muted">Overview of all perfume brands.</p></div>
        <div className="row-gap">
          <input className="input" placeholder="Search..." value={q} onChange={(e) => setQ(e.target.value)} />
          <button className="btn" onClick={() => setQ("")}>Refresh</button>
        </div>
      </header>

      <form className="panel" onSubmit={handleSubmit} style={{ marginBottom: 12, display: "flex", gap: 8, flexWrap: "wrap", alignItems: "center" }}>
        <input className="input" placeholder="Name" value={newName} onChange={(e) => setNewName(e.target.value)} />
        <input className="input" placeholder="Country" value={newCountry} onChange={(e) => setNewCountry(e.target.value)} />
        
        <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
          <input className="input" placeholder="Image URL" value={newImageUrl} onChange={(e) => setNewImageUrl(e.target.value)} />
          {newImageUrl.trim() && (
            <img 
              src={newImageUrl.trim()} 
              alt="Preview" 
              style={{ width: 40, height: 40, objectFit: "cover", borderRadius: 4, border: "1px solid #ccc" }}
              onError={(e) => { e.target.style.display = 'none'; }}
            />
          )}
        </div>

        <button className="btn primary" type="submit">{editingID == null ? "Save" : "Update"}</button>
        {editingID != null && <button className="btn" type="button" onClick={cancelUpdate}>Cancel</button>}
      </form>

      <table className="table">
        <thead>
          <tr>
            <th onClick={() => toggleSort("id")}>ID {sort.by === "id" && (sort.dir === "asc" ? "▲" : "▼")}</th>
            <th onClick={() => toggleSort("name")}>Name {sort.by === "name" && (sort.dir === "asc" ? "▲" : "▼")}</th>
            <th onClick={() => toggleSort("country")}>Country {sort.by === "country" && (sort.dir === "asc" ? "▲" : "▼")}</th>
            <th>Image</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {prikazani.map((r) => (
            <TableRowBrand key={r.id} id={r.id} name={r.name} country={r.country} imageUrl={r.imageUrl} onDelete={handleDelete} onUpdate={() => startUpdate(r)} />
          ))}
        </tbody>
      </table>
      <div className='filler'></div>
    </div>
  )
}

export default Brands;
