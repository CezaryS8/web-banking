import React, { useState } from "react";
import CardsView from "../card/CardsView";
import TransferComponent from "../transfer/TransfersComponent";

const AccountComponent = ({ account }) => {
  const [showCards, setShowCards] = useState(false);

  return (
    <div onClick={() => setShowCards(!showCards)}>
      ------------------------------------------------------------------------------------------------------
      <TransferComponent
        accountNumber={account.accountNumber}
        accountBalance={account.balance}
      />
      <CardsView accountId={account.id} />
      ------------------------------------------------------------------------------------------------------
    </div>
  );
};

export default AccountComponent;
