import React, { useState } from "react";
import http from "../api/http";
import "../css/auth.css";

export default function InitiateRegistration() {
  const [email, setEmail] = useState("");
  const [loading, setLoading] = useState(false);
  const [ok, setOk] = useState("");
  const [err, setErr] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setErr(""); setOk(""); setLoading(true);
    try {
      await http.post("/auth/initiate-registration", { email });
      setOk("Check your email! We sent you a verification link.");
    } catch (e2) {
      setErr(e2?.response?.data?.message || "Email already in use!");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2>Create account</h2>
        <p className="muted">Enter your email to get started</p>

        {err && <div className="auth-alert">{err}</div>}
        {ok && <div className="auth-success">{ok}</div>}

        {!ok && (
          <form onSubmit={handleSubmit} className="auth-form">
            <div className="field">
              <label>Email</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            <button className="btn-primary" disabled={loading}>
              {loading ? "Sending…" : "Send verification link"}
            </button>
          </form>
        )}

        <div className="auth-footer">
          <span className="muted">Already have an account?</span>
          <a href="/login">Sign in</a>
        </div>
      </div>
    </div>
  );
}