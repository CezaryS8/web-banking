import { useLocation, Navigate, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";
import { selectCurrentToken } from "./authSlice";
import LogoutButton from "../../components/auth/LogoutButton";

// TODO: role check
const RequireAuth = () => {
  const token = useSelector(selectCurrentToken);
  const location = useLocation();

  return token ? (
    <>
      <LogoutButton />
      <Outlet />
    </>
  ) : (
    <Navigate to="/login" state={{ from: location }} replace />
  );
};

export default RequireAuth;
