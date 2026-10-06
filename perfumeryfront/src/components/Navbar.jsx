import React from "react";
import { Link, NavLink, useNavigate } from "react-router-dom";
import "../css/navbar.css";

function isAuthed() {
  return !!localStorage.getItem("token");
}

function getMe() {
  try {
    return JSON.parse(localStorage.getItem("me") || "null");
  } catch {
    return null;
  }
}

export default function Navbar() {
  const nav = useNavigate();
  const authed = isAuthed();
  const me = getMe();

  function handleLogout() {
    localStorage.removeItem("token");
    localStorage.removeItem("me");
    nav("/login");
  }

  const active = ({ isActive }) => ({
    color: isActive ? "var(--cream)" : "var(--muted)",
    fontWeight: isActive ? 700 : 500,
  });

  return (
    <header className="nav">
      <div className="container nav-inner">
        <Link to="/" className="brand" style={{ textDecoration: "none", color: "var(--cream)", fontWeight: 900 }}>
          Parfum<span style={{ color: "var(--olive)" }}>erie</span>
        </Link>

        <nav className="nav-links">
          <NavLink to="/" style={active}>Home Page</NavLink>
          {authed && me?.role === "ADMIN" && (
            <>
              <NavLink to="/perfumes" style={active}>Perfumes</NavLink>
              <NavLink to="/brands" style={active}>Brands</NavLink>
              <NavLink to="/admin/orders" style={active}>Orders</NavLink>
            </>
          )}
          {authed && me?.role === "CLIENT" && (
            <>
              <NavLink to="/brands_page" style={active}>Brands</NavLink>
              <NavLink to="/cart" style={active}>Cart</NavLink>
            </>
          )}
        </nav>

        <div className="nav-actions">
          {!authed ? (
            <>
              <Link className="btn-outline" to="/login">Login</Link>
              <Link className="btn-primary" to="/register">Register</Link>
            </>
          ) : (
            <>
              <span className="user-chip">{me?.username ?? "User"}</span>
              <button className="btn-outline" onClick={handleLogout}>Logout</button>
            </>
          )}
        </div>
      </div>
    </header>
  );
}