import React from "react";

const CardComponent = ({ cardType, expirationDate }) => {
  return (
    <div>
      <p>Card Type: {cardType}</p>
      <p>Expiration Date: {expirationDate}</p>
    </div>
  );
};

export default CardComponent;
