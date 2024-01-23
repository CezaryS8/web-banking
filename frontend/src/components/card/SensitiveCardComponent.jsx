import React, { useState, useEffect } from "react";
import {
  useGetCodeToCardSensitiveMutation,
  useGetCardSensitiveMutation,
} from "../../features/user/cards/cardsApiSlice";

const SensitiveCardComponent = ({ cardId, accountId }) => {
  const [
    getCodeToCardSensitive,
    {
      isLoading: isSendingCode,
      isSuccess: isSendingCodeSuccess,
      isError: isSendingCodeError,
      error: sendingCodeError,
    },
  ] = useGetCodeToCardSensitiveMutation();

  const [
    getCardSensitive,
    {
      data: sensitiveCardData,
      isLoading: isCardSensitiveLoading,
      isSuccess: isCardSensitiveSuccess,
      isError: isCardSensitiveError,
      error: cardSensitiveError,
    },
  ] = useGetCardSensitiveMutation();

  const [statusMessage, setStatusMessage] = useState("");
  const [showCodeInput, setShowCodeInput] = useState(false);
  const [code, setCode] = useState("");

  const handleGetCodeClick = async () => {
    try {
      await getCodeToCardSensitive({ accountId, cardId }).unwrap();
      setStatusMessage("Code sended to your email/phone.");
      setShowCodeInput(true);
    } catch (err) {
      setStatusMessage("Something went wrong. Try again later.");
    }
  };

  const handleAcceptCodeClick = async () => {
    try {
      await getCardSensitive({
        accountId: accountId,
        cardId: cardId,
        code: code,
      }).unwrap();
      setCode("");
      setShowCodeInput(false);
      setStatusMessage(
        "For demonstration purposes, the code is seen for 3 seconds."
      );
    } catch (error) {
      setStatusMessage("Invalid code. Try again");
    }
  };

  const [temporarySensitiveData, setTemporarySensitiveData] = useState(null);

  useEffect(() => {
    if (sensitiveCardData) {
      setTemporarySensitiveData(sensitiveCardData);

      const timeoutId = setTimeout(() => {
        setTemporarySensitiveData(null);
      }, 3000);

      return () => clearTimeout(timeoutId);
    }
  }, [sensitiveCardData]);

  return (
    <div>
      {!showCodeInput && (
        <button
          onClick={handleGetCodeClick}
          disabled={isSendingCode || isCardSensitiveLoading}
        >
          {isSendingCode ? "Loading..." : "Get Code"}
        </button>
      )}
      {showCodeInput && (
        <div>
          <input
            type="text"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            maxLength={6}
            placeholder="Enter 6-digit code"
          />
          <button
            onClick={handleAcceptCodeClick}
            disabled={code.length !== 6 || isCardSensitiveLoading}
          >
            {isCardSensitiveLoading ? "Validating..." : "Accept Code"}
          </button>
        </div>
      )}
      {temporarySensitiveData ? (
        <div>
          <p>Card Number: {sensitiveCardData.cardNumber}</p>
          <p>CVC: {sensitiveCardData.cvc}</p>
        </div>
      ) : (
        <div>
          <p>Card Number: ****************</p>
          <p>CVC: ***</p>
        </div>
      )}

      {statusMessage && <p>{statusMessage}</p>}
      {isCardSensitiveError && cardSensitiveError && (
        <p>Error: {cardSensitiveError.message}</p>
      )}
    </div>
  );
};

export default SensitiveCardComponent;
