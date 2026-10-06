import React from 'react'
import { useNavigate } from "react-router-dom";
import "./../css/Pocetna.css";

export default function Pocetna() {
  const navigate = useNavigate();

  const categories = [
    { name: "Men's Perfumes",   gender: "MALE",   img: "https://labelleperfumes.com/cdn/shop/files/armani-stronger-with-you-parfum-men-a.webp1_800x.webp?v=1755633187" },
    { name: "Women's Perfumes", gender: "FEMALE", img: "https://www.luxe-infinity.com/wp-content/uploads/2018/07/lancome3.jpg" },
    { name: "Unisex Perfumes",  gender: "UNISEX", img: "https://mojaparfimerija.com/px_image/male/Hugo-Boss-Boss-The-Scent-Intense-muski-i-zenski-parfem-reklama.jpg" },
  ];

  const brands = [
  { id: 1, name: "Chanel",          country: "France", img: "https://cdn.media.amplience.net/i/frasersdev/75937769_o_a1?v=20240417082012?fmt=auto&upscale=true&w=992&h=992&sm=scaleFit&$h-ttl$" },
  { id: 4, name: "Dolce & Gabbana", country: "Italy",  img: "https://perfumedubai.com/cdn/shop/articles/dolceandgabbana-theone-hybrid-gb-170423_1_1_1_1024x1024.jpg?v=1698917411" },
  { id: 6, name: "Michael Kors",    country: "USA",    img: "https://michaelkors.scene7.com/is/image/MichaelKors/5JT6-01-9999_8?$pdplarge$" },
  ];

  return (
    <>
      <section className="hero">
        <div className="container hero-grid">
          <div className="hero-copy">
            <span className="pill">⭐ 4.9 • trusted by luxury brands</span>
            <h1>Discover your <br /> signature scent</h1>
            <p>Browse premium perfume brands, find your favorite fragrance, and order with just a few clicks.</p>
            <div className="stats">
              <div><strong>50+</strong><span>Brands</span></div>
              <div><strong>300+</strong><span>Perfumes</span></div>
              <div><strong>Fast</strong><span>Delivery</span></div>
            </div>
          </div>
          <div className="hero-art">
            <div className="hero-card big">
              <img src="https://i.pinimg.com/736x/f5/68/1e/f5681e8f0e40d8499de72ff41fa2b0a5.jpg" alt="Perfume" />
              <div className="badge">Top brand of the week</div>
            </div>
            <div className="hero-card small">
              <img src="https://michaelkors.scene7.com/is/image/MichaelKors/5JT6-01-9999_9?$pdplarge$" alt="Perfume" />
              <div className="badge">New arrivals</div>
            </div>
          </div>
        </div>
      </section>

      <section className="section">
        <div className="container">
          <div className="section-head"><h2>Categories</h2></div>
          <div className="grid cats">
            {categories.map((c) => (
              <div
                key={c.name}
                className="cat"
                onClick={() => navigate(`/category/${c.gender}`)}
                style={{ cursor: "pointer" }}
              >
                <img src={c.img} alt={c.name} />
                <span>{c.name}</span>
              </div>
            ))}
          </div>
        </div>
      </section>

      <section className="section alt">
        <div className="container">
          <div className="section-head"><h2>Featured Brands</h2></div>
          <div className="grid rest">
            {brands.map((b) => (
                <article 
                   className="rest-card" 
                   key={b.id}
                   onClick={() => navigate(`/perfumes_page/${b.id}`)}
                    style={{ cursor: "pointer" }}
                >
                <div className="thumb">
                  <img src={b.img} alt={b.name} />
                </div>
                <div className="content">
                  <h3>{b.name}</h3>
                  <p>{b.country}</p>
                </div>
              </article>
            ))}
          </div>
        </div>
      </section>
    </>
  );
}