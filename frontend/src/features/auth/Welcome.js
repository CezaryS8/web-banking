import { useSelector } from "react-redux";
import { selectCurrentUser, selectCurrentToken } from "./authSlice";
import { Link } from "react-router-dom";
import LogoutButton from "../../components/auth/LogoutButton";

const Welcome = () => {
  const user = useSelector(selectCurrentUser);
  const token = useSelector(selectCurrentToken);

  const welcome = user ? `Welcome ${user.username}!` : "Welcome!";

  const content = (
    <section className="welcome">
      <h1>{welcome}</h1>
      <h4>
        This bank didn't have enought time for UI/UX, but relax, you money is
        secure.
      </h4>
      <p>
        <Link to="/accounts">Your Accounts</Link>
      </p>
    </section>
  );

  return content;
};

export default Welcome;
