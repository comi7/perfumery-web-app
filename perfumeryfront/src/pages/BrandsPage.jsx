import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import http from "../api/http";
import "../css/shop.css";

// RECNIK SLIKA: kao sa parfemima, ovde rucno dodeljujem sliku svakom brendu preko njegovog ID-ja
// const SLIKE_BRENDOVA = {
//   1: "https://images.microcms-assets.io/assets/b03468ea88f84bed971667dea3af6cfd/c3215e3abd664db4b36cbcbea2908e66/FR_NAAR_1000x640.jpg", // Chanel 
//   2: "https://storage.ghost.io/c/70/de/70de4e1c-4ddb-426b-80d4-d01814c2ae61/content/images/2024/02/Explore-our-comprehensive-guide-to-finding-your-perfect-signature-scent--covering-fragrance-families--personality-assessment--and-testing-tips.-Unveil-the-secrets-to-confidently-choosing-the-right--36-.png", // Dior
//   3: "https://cdn.notinoimg.com/detail_main_lq/armani/odg4/acqua-di-gioia-ocean___220407.jpg", // Armani 
//   4: "https://m.media-amazon.com/images/I/71mC2JyS62L.jpg", // D&G 
//   5: "https://editorialist.com/wp-content/uploads/2022/08/versace-perfumes-social.png", // Versace
//   6: "https://www.myperfumeshop.bh/cdn/shop/files/michael-kors-gorgeous-edp-perfume-cologne-863343.webp?v=1722721810&width=1654", // Michael Kors
//   7: "https://thebeautystore.com/cdn/shop/articles/3074-h1_e2bf1ca2-0a35-44b8-b330-5e115caaf538.webp?v=1761928455&width=1100", // Rabanne
//   8: "https://bagallery.com/cdn/shop/collections/ck_1200x.jpg?v=1674114250" // Calvin Klein
// };

const BrandsPage = () => {
  const [brands, setBrands] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    http.get("/brand")
      .then((res) => {
        const podaci = Array.isArray(res.data) ? res.data : [];
        
        // sortiranje brendova abecedno prema nazivu drzave (b.country)
        const sortiraniBrendovi = [...podaci].sort((a, b) => {
          const drzavaA = String(a.country ?? "").toLowerCase();
          const drzavaB = String(b.country ?? "").toLowerCase();
          return drzavaA.localeCompare(drzavaB);
        });

        setBrands(sortiraniBrendovi);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <div style={{ padding: "2rem" }}>Loading...</div>;

  return (
    <div className="rest-grid">
      {brands.map((b) => (
        <div key={b.id} className="rest-card" onClick={() => navigate(`/perfumes_page/${b.id}`)} role="button" tabIndex={0}>
          <div className="thumb">
            {/* prema b.id se uzima slika iz recnika, 
                ako brend nema sliku u recniku, prikazace staru sliku sa plavom pozadinom kao rezervnu */}
            <img
              src={b.imageUrl || "https://images.unsplash.com/photo-1541643600914-78b084683601?auto=format&fit=crop&w=400"}
              alt={b.name}
              loading="lazy"
            />
          </div>
          <div className="content">
            <h3>{b.name}</h3>
            <p>{b.country}</p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default BrandsPage;
