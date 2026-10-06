import React, { useState, useEffect, useCallback } from "react";
import { Routes, Route, BrowserRouter } from "react-router-dom";
import "./App.css";
import Pocetna from "./pages/Pocetna";
import { Footer } from "./components/Footer";
import Perfumes from "./pages/Perfumes";
import { Brands } from "./pages/Brands";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";
import BrandsPage from "./pages/BrandsPage";
import PerfumesPage from "./pages/PerfumesPage";
import CartPage from "./pages/CartPage";
import AdminOrdersPage from "./pages/AdminOrdersPage";
import AboutUs from "./pages/About";
import Contact from "./pages/Contact";
import CategoryPage from "./pages/CategoryPage";
import InitiateRegistration from "./pages/InitiateRegistration";
import CompleteRegistration from "./pages/CompleteRegistration";


function App() {
  const [cart, setCart] = useState(() => {
    try {
      return JSON.parse(localStorage.getItem("cart")) || [];
    } catch {
      return [];
    }
  });

  useEffect(() => {
    localStorage.setItem("cart", JSON.stringify(cart));
  }, [cart]);

  const [userId, setUserId] = useState(null);

  useEffect(() => {
    try {
      const me = JSON.parse(localStorage.getItem("me"));
      setUserId(me?.id ?? null);
    } catch (e) {
      setUserId(null);
    }
  }, []);

  useEffect(() => {
    const onStorage = (e) => {
      if (e.key === "me") {
        try {
          const me = JSON.parse(e.newValue);
          setUserId(me?.id ?? null);
        } catch {
          setUserId(null);
        }
      }
    };
    window.addEventListener("storage", onStorage);
    return () => window.removeEventListener("storage", onStorage);
  }, []);

  const addToCart = useCallback((perfume) => {
    const makeId =
      (typeof crypto !== "undefined" && crypto.randomUUID && crypto.randomUUID()) ||
      `${Date.now()}-${Math.random()}`;

    setCart((prev) => {
      const existing = prev.find((x) => x.id === perfume.id);
      if (existing) {
        return prev.map((x) =>
          x.id === perfume.id
            ? { ...x, quantity: (x.quantity || 1) + 1 }
            : x
        );
      }
      return [
        ...prev,
        {
          cartId: makeId,
          id: perfume.id,
          name: perfume.name,
          price: perfume.price,
          gender: perfume.gender,
          fragranceType: perfume.fragranceType,
          volumeMl: perfume.volumeMl,
          quantity: 1,
        },
      ];
    });
  }, []);

  return (
    <BrowserRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<Pocetna />} />
        <Route path="/perfumes" element={<ProtectedRoute><Perfumes /></ProtectedRoute>} />
        <Route path="/brands" element={<ProtectedRoute><Brands /></ProtectedRoute>} />
        <Route path="/admin/orders" element={<ProtectedRoute><AdminOrdersPage /></ProtectedRoute>} />
        <Route path="/login" element={<Login onSuccess={() => (window.location.href = "/")} />} />
        <Route path="/register" element={<InitiateRegistration />} />
        <Route path="/complete-registration" element={<CompleteRegistration onSuccess={() => (window.location.href = "/")} />} />
        <Route path="/about" element={<AboutUs />} />
        <Route path="/contact" element={<Contact />} />
        <Route path="/brands_page" element={<BrandsPage />} />
        <Route path="/category/:gender" element={<CategoryPage addToCart={addToCart} />} />
        <Route path="/perfumes_page/:id" element={<PerfumesPage addToCart={addToCart} />} />
        <Route path="/cart" element={<CartPage cart={cart} setCart={setCart} userId={userId} />} />
      </Routes>
      <Footer />
    </BrowserRouter>
  );
}

export default App;