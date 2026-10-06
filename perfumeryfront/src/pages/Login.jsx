import React, { useState } from "react";
import http from "../api/http";
import "../css/auth.css";

export default function Login({ onSuccess }) {
  const [form, setForm] = useState({ username: "", password: "" });
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErr("");
    setLoading(true);
    try {
      const res = await http.post("/auth/login", form);
      localStorage.setItem("token", res.data.token);
      localStorage.setItem("me", JSON.stringify(res.data.user));
      if (typeof onSuccess === "function") onSuccess(res.data.user);
    } catch (e2) {
      setErr(e2?.response?.data?.message || "Login failed");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2>Welcome back</h2>
        <p className="muted">Sign in to continue</p>
        {err && <div className="auth-alert">{err}</div>}
        <form onSubmit={handleSubmit} className="auth-form">
          <div className="field">
            <label>Username</label>
            <input type="text" value={form.username} onChange={(e) => setForm((f) => ({ ...f, username: e.target.value }))} required />
          </div>
          <div className="field">
            <label>Password</label>
            <input type="password" value={form.password} onChange={(e) => setForm((f) => ({ ...f, password: e.target.value }))} required />
          </div>
          <button className="btn-primary" disabled={loading}>{loading ? "Signing in…" : "Sign in"}</button>
        </form>
        <div className="auth-footer">
          <span className="muted">Don't have an account?</span>
          <a href="/register">Create one</a>
        </div>
      </div>
    </div>
  );
}