import React, { useState } from "react";
import { useCreateTransferMutation } from "../../features/user/transfers/transfersApiSlice";

const TransferComponent = ({ accountNumber, accountBalance }) => {
  const [showForm, setShowForm] = useState(false);
  const [transferDetails, setTransferDetails] = useState({
    fromAccountNumber: accountNumber,
    toAccountNumber: "",
    amount: "",
    title: "",
  });

  const [createTransfer, { isLoading }] = useCreateTransferMutation();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setTransferDetails({
      ...transferDetails,
      [name]: value,
    });
  };

  const isValidAccountNumber = (number) => /^\d{26}$/.test(number);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!isValidAccountNumber(transferDetails.toAccountNumber)) {
      alert("Invalid 'To Account Number'. It should have 26 digits.");
      return;
    }

    const amount = parseFloat(transferDetails.amount);
    if (isNaN(amount) || amount < 0.1 || amount > 1000000.0) {
      alert(
        "Invalid amount. The amount should be between 0.1 and 1,000,000.0."
      );
      return;
    }

    if (amount > accountBalance) {
      alert("Insufficient funds.");
      return;
    }

    if (
      transferDetails.title.length < 2 ||
      transferDetails.title.length > 100
    ) {
      alert(
        "Invalid title. The title length should be between 2 and 100 characters."
      );
      return;
    }

    try {
      await createTransfer(transferDetails).unwrap();
      setTransferDetails({
        fromAccountNumber: accountNumber,
        toAccountNumber: "",
        amount: "",
        title: "",
      });
      setShowForm(false);
      alert("Transfer created successfully.");
    } catch (err) {
      alert("Failed to create transfer: ", err);
    }
  };

  return (
    <div>
      <div>
        <h4>Account Number: {accountNumber}</h4>
        <p>Account Balance: PLN {accountBalance.toFixed(2)}</p>
      </div>
      <button onClick={() => setShowForm(!showForm)}>Transfer Money</button>
      {showForm && (
        <div>
          <form onSubmit={handleSubmit}>
            <input
              type="text"
              name="fromAccountNumber"
              value={transferDetails.fromAccountNumber}
              readOnly
            />
            <input
              type="text"
              name="toAccountNumber"
              value={transferDetails.toAccountNumber}
              onChange={handleInputChange}
              placeholder="To Account Number"
              required
            />
            <input
              type="number"
              name="amount"
              value={transferDetails.amount}
              onChange={handleInputChange}
              placeholder="Amount"
              max={accountBalance}
              step="0.01"
              required
            />
            <input
              type="text"
              name="title"
              value={transferDetails.title}
              onChange={handleInputChange}
              placeholder="Title"
              required
            />
            <button
              type="submit"
              disabled={
                isLoading || parseFloat(transferDetails.amount) > accountBalance
              }
            >
              Send Transfer
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default TransferComponent;
