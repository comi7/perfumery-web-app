import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import http from "../api/http";
import "../css/shop.css";


/*const SLIKE_PARFEMA = {  --- smestila sam ih u zaseban fajl ipak ----
  1: "https://cdn.flaconi.net/media/catalog/product/3/6/3614274298024-1_c_live.jpg?r=1WAHCg&c=at&w=1200&q=80", // Armani Code M
  2: "https://media.cdn.kaufland.de/product-images/1024x1024/a6b21b471438550d9f24630d7da79671.jpg", // Light Blue M
  3: "https://www.parfimerijatajna.rs/files/2020/02/unnamed-1.jpg", // Emporio He
  4: "https://images.kaina24.lt/1567/20/giorgio-armani-emporio-she-edp-100-ml.jpg", // Emporio She
  5: "https://lotusparfemshop.com/files/sync_pictures/products/2023.10.05.12.48.48_versacebrightcrystalabsolu90ml_inner.jpg", // Bright Crystal Absolu F
  6: "https://www.boutiquederoyal.com/cdn/shop/files/2_e2889eeb-b35f-4663-9238-57ddd81a5c0a_720x.jpg?v=1752324975", // Chanel No. 5 F
  7: "https://www.parfemilux.rs/wp-content/uploads/2023/07/IMG_1094.jpeg", // Bleu de Chanel M
  8: "https://www.parfemilux.rs/wp-content/uploads/2023/07/photo-output-17.jpeg", // Coco Chanel Noir F
  9: "https://www.parfemilux.rs/wp-content/uploads/2023/07/photo-output-53.jpeg", // Dior Sauvage M
  10: "https://aromaparfemi.com/cdn/shop/files/2a_2c2b8a88-5b40-47ce-a257-5419cb13e189.jpg?v=1730539227&width=1445", // Miss Dior F
  11: "https://ifragranceofficial.com/wp-content/uploads/2025/10/acqua-di-gio-elixir-2025.webp", // Acqua di Gio M
  12: "https://m.media-amazon.com/images/I/61e04tRX7AL._AC_UF894,1000_QL80_.jpg", // Light Blue F
  13: "https://parfembox.rs/wp-content/uploads/2024/11/dolce-gabbana-the-one-75ml-edp.png", // The one F
  14: "https://mojaparfimerija.com/px_image/male/Michael-Kors-Wonderlust-50ml-edp-zenski-parfem.jpg", // Wonderlust F
  15: "data:image/jpeg;base64,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", // Paco Rabanne unisex
  16: "https://static.beautytocare.com/media/catalog/product/c/a/calvin-klein-ck-one-eau-de-toilette-200ml_1.jpg", // One unisex
  17: "https://cdn.idealo.com/folder/Product/2248/9/2248995/s3_produktbild_gross_7/paco-rabanne-lady-million-eau-de-parfum-50ml.jpg" // Lady Million
}; */

const PerfumesPage = ({ addToCart }) => {
  const { id } = useParams();
  const [perfumes, setPerfumes] = useState([]);

  useEffect(() => {
    http.get(`/perfume/brand/${id}`).then((res) => setPerfumes(res.data));
  }, [id]);

  const me = JSON.parse(localStorage.getItem("me") || "null");
  return (
    <div className="dishes-grid">
      {perfumes.map((p) => (
        <div key={p.id} className="dish">
          {/* proverava da li postoji slika za taj ID u objektu SLIKE_PARFEMA,
              inace prikazuje staru sliku sa ruzama kao rezervnu */}
          
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
  );
};

export default PerfumesPage;
