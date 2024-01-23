import React, { useEffect } from "react";
import { useGetAccountsQuery } from "./accountsApiSlice";
import { Link } from "react-router-dom";
import AccountsView from "../../../components/account/AccountsView";

const Accounts = () => {
  const {
    data: accounts,
    isLoading,
    isSuccess,
    isError,
    error,
    refetch,
  } = useGetAccountsQuery();

  useEffect(() => {
    refetch();
  }, [refetch]);

  let content;
  if (isLoading) {
    content = <p>"Loading..."</p>;
  } else if (isSuccess) {
    content = (
      <section className="accounts">
        <Link to="/welcome">Back to Welcome</Link>
        <h1>Accounts</h1>
        <ul>
          {accounts.map((account) => (
            <li key={account.id}>
              {account.id} | {account.accountNumber} | {account.balance}
            </li>
          ))}
        </ul>
        <AccountsView accounts={accounts} />
      </section>
    );
  } else if (isError) {
    content = <p>{JSON.stringify(error)}</p>;
  }

  return content;
};

export default Accounts;
