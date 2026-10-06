import React from "react";

export default function ProtectedRoute({ children }) {
  const token = localStorage.getItem("token");
  if (!token) {
    return (
      <div style={{ padding: "2rem", textAlign: "center" }}>
        <h2>❌ Morate se prvo ulogovati</h2>
      </div>
    );
  }
  return children;
}