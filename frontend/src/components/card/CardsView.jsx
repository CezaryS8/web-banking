import React from "react";
import { useGetCardsQuery } from "../../features/user/cards/cardsApiSlice";
import SensitiveCardComponent from "./SensitiveCardComponent";
import InsensitiveCardComponent from "./InsensitiveCardComponent";
const CardsView = ({ accountId }) => {
  const {
    data: cards,
    isLoading,
    isSuccess,
    isError,
    error,
  } = useGetCardsQuery(accountId);

  let content;
  if (isLoading) {
    content = <p>"Loading..."</p>;
  } else if (isSuccess) {
    content = (
      <section className="cards">
        <h1>Cards</h1>
        <ul>
          ---------------------------------------------------------
          {cards.map((card) => (
            <>
              <InsensitiveCardComponent
                key={"insensitive-" + card.id}
                cardId={card.id}
                accountId={accountId}
              />
              <SensitiveCardComponent
                key={"sensitive-" + card.id}
                cardId={card.id}
                accountId={accountId}
              />
              ---------------------------------------------------------
            </>
          ))}
        </ul>
      </section>
    );
  } else if (isError) {
    content = <p>{JSON.stringify(error)}</p>;
  }
  return content;
};

export default CardsView;
