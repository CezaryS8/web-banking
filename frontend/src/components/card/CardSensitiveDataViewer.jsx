import React, { useState } from "react";

const CardSensitiveDataViewer = () => {
  const [sensitiveData, setSensitiveData] = useState("");

  const fetchSensitiveData = () => {
    const id = "1";
    fetch(`/api/getSensitiveData?id=${id}`)
      .then((response) => response.text())
      .then((data) => setSensitiveData(data));
  };

  return (
    <div>
      <button onClick={fetchSensitiveData}>Wyświetl dane wrażliwe</button>
      <div>{sensitiveData}</div>
    </div>
  );
};

export default CardSensitiveDataViewer;
