import React from 'react'
import { MdDelete } from 'react-icons/md';
import { FaEdit } from "react-icons/fa";

const TableRowBrand = ({ id, name, country, imageUrl, onDelete, onUpdate }) => {
  return (
    <tr>
      <td>{id}</td>
      <td>{name}</td>
      <td>{country}</td>
      <td>
        {imageUrl
          ? <img src={imageUrl} alt={name} style={{ width: 50, height: 50, objectFit: "cover", borderRadius: 4 }} />
          : <span style={{ color: "#aaa", fontSize: "0.8rem" }}>No image</span>
        }
      </td>
      <td>
        <button className="btn tiny" onClick={onUpdate} style={{ marginRight: 8 }}><FaEdit /></button>
        <button className="btn tiny danger" onClick={() => onDelete(id)}><MdDelete /></button>
      </td>
    </tr>
  )
}

export default TableRowBrand;
