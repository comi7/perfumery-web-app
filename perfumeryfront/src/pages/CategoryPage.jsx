import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import http from "../api/http";
import "../css/shop.css";


const CategoryPage = ({ addToCart }) => {
  const { gender } = useParams(); // "MALE", "FEMALE", "UNISEX"
  const [perfumes, setPerfumes] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    http.get("/perfume")
      .then((res) => {
        const svi = Array.isArray(res.data) ? res.data : [];
        const filtrirani = svi.filter(
          (p) => p.gender === gender.toUpperCase()
        );
        setPerfumes(filtrirani);
      })
      .finally(() => setLoading(false));
  }, [gender]);

  if (loading) return <div style={{ padding: "2rem" }}>Loading...</div>;
  const me = JSON.parse(localStorage.getItem("me") || "null");
  return (
    <div>
      <div style={{ padding: "2rem 1rem 0", maxWidth: 1200, margin: "0 auto" }}>
        <h2 style={{ color: "#1a1a2e", textTransform: "capitalize" }}>
          {gender === "MALE" ? "Men's Perfumes" : gender === "FEMALE" ? "Women's Perfumes" : "Unisex Perfumes"}
        </h2>
      </div>
      <div className="dishes-grid">
        {perfumes.length === 0 && <p style={{ padding: "1rem" }}>No perfumes found.</p>}
        {perfumes.map((p) => (
          <div key={p.id} className="dish">
            <img
              src={p.imageUrl || "https://images.unsplash.com/photo-1592945403244-b3fbafd7f539?auto=format&fit=crop&w=400"}
              alt={p.name}
              loading="lazy"
            />
            <div className="row">
              <h4>{p.name}</h4>
              <span className="price">{p.price} RSD</span>
            </div>
            <p className="muted">{p.gender} · {p.fragranceType} · {p.volumeMl}ml</p>
            {me?.role !== "ADMIN" && (
            <button
              onClick={() => {
                if (!me) {
                  alert("Please log in or register to add items to cart!");
                  return;
                }
                addToCart({ id: p.id, name: p.name, price: p.price, gender: p.gender, fragranceType: p.fragranceType, volumeMl: p.volumeMl });
                alert(`${p.name} added to cart!`);
              }}
              className="btn-add"
            >
              Add to Cart
            </button>
          )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default CategoryPage;