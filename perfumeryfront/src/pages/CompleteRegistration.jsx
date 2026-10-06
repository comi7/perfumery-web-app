import React, { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import http from "../api/http";
import "../css/auth.css";

export default function CompleteRegistration({ onSuccess }) {
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [err, setErr] = useState("");

  useEffect(() => {
    if (!token) setErr("Invalid or missing token.");
  }, [token]);

  async function handleSubmit(e) {
    e.preventDefault();
    setErr(""); setLoading(true);
    try {
      await http.post("/auth/complete-registration", { token, username, password });

      // Auto-login nakon registracije
      const loginRes = await http.post("/auth/login", { username, password });
      localStorage.setItem("token", loginRes.data.token);
      localStorage.setItem("me", JSON.stringify(loginRes.data.user));

      if (typeof onSuccess === "function") onSuccess(loginRes.data.user);
    } catch (e2) {
      setErr(e2?.response?.data?.message || "Registration failed.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="auth-wrap">
      <div className="auth-card">
        <h2>Complete registration</h2>
        <p className="muted">Choose your username and password</p>

        {err && <div className="auth-alert">{err}</div>}

        <form onSubmit={handleSubmit} className="auth-form">
          <div className="field">
            <label>Username</label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              minLength={3}
            />
          </div>
          <div className="field">
            <label>Password</label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              minLength={6}
            />
          </div>
          <button className="btn-primary" disabled={loading || !token}>
            {loading ? "Creating account…" : "Create account"}
          </button>
        </form>
      </div>
    </div>
  );
}