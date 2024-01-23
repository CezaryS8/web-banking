import React from "react";
import { useGetCardInsensitiveQuery } from "../../features/user/cards/cardsApiSlice";

const InsensitiveCardComponent = ({ cardId, accountId }) => {
  const {
    data: card,
    isLoading,
    isSuccess,
    isError,
    error,
  } = useGetCardInsensitiveQuery({ accountId, cardId });

  let content;
  if (isLoading) {
    content = <p>"Loading..."</p>;
  } else if (isSuccess) {
    content = (
      <div>
        <p>Card Type: {card.cardType}</p>
        <p>Expiration Date: {card.expirationDate}</p>
      </div>
    );
  } else if (isError) {
    content = <p>Something wrong.</p>;
  }
  return content;
};

export default InsensitiveCardComponent;
