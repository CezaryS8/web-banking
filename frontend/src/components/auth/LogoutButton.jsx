import React from "react";
import { useDispatch } from "react-redux";
import { logOut } from "../../features/auth/authSlice";

const LogoutButton = () => {
  const dispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logOut());
  };

  return <button onClick={handleLogout}>Logout</button>;
};

export default LogoutButton;
