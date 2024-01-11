import { Link } from "react-router-dom";

const Public = () => {
  const content = (
    <section className="public">
      <header>
        <h1>Welcome to CBank!</h1>
      </header>
      {/* <main>BANK</main> */}
      <footer>
        <Link to="/login">Login</Link>
      </footer>
    </section>
  );
  return content;
};
export default Public;
