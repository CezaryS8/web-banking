import { useGetAccountsQuery } from "./accountsApiSlice";
import { Link } from "react-router-dom";

const Accounts = () => {
  const {
    data: accounts,
    isLoading,
    isSuccess,
    isError,
    error,
  } = useGetAccountsQuery();

  console.log(accounts);

  let content;
  if (isLoading) {
    content = <p>"Loading..."</p>;
  } else if (isSuccess) {
    content = (
      <section className="users">
        <h1>Accounts</h1>
        <ul>
          {accounts.map((account, i) => {
            return (
              <>
                <li key={i}>
                  {account.id} | {account.accountNumber} | {account.balance}
                </li>
              </>
            );
          })}
        </ul>
        <Link to="/welcome">Back to Welcome</Link>
      </section>
    );
  } else if (isError) {
    content = <p>{JSON.stringify(error)}</p>;
  }

  return content;
};

export default Accounts;
