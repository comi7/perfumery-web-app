import React from 'react'

export const Footer = () => {
  return (
    <footer id="contact" className="footer">
      <div className="container foot">
        <div className="brand">
          <div className="dot"></div>
          <span>Your favorite online perfumery.</span>
        </div>
        <ul className="foot-links">
          <li><a href="/about">About Us</a></li>
          <li><a href="/contact">Contact</a></li>
        </ul>
        <small>© {new Date().getFullYear()} Parfumerie</small>
      </div>
    </footer>
  )
}