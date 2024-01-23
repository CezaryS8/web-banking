import React from "react";
import AccountComponent from "./AccountComponent";

const AccountsView = ({ accounts }) => {
  return (
    <div>
      {accounts.map((account) => (
        <AccountComponent key={account.id} account={account} />
      ))}
    </div>
  );
};

export default AccountsView;
