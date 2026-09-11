import { useEffect, useState } from "react";
import { Link, Outlet } from "react-router";
import { apiFetch } from "./api";

type User = {
  username: string;
};

export default function Layout() {
  const [user, setUser] = useState<User | undefined>();

  async function loadUser() {
    try {
      const response = await apiFetch("/api/users/me");
      if (response.status === 401) {
        throw new Error("User is not logged in");
      }
      const data = await response.json();
      setUser(data);
    } catch (e: any) {
      console.error("Failed to load user", e);
    }
  }

  useEffect(() => {
    loadUser();
  }, []);

  return (
    <>
      <header className="mx-8 my-4 flex">
        {user ? (
          <div>Welcome back, {user?.username}!</div>
        ) : (
          <div>Welcome back, guest!</div>
        )}

        <div className="ml-4 flex gap-2">
          <Link to="/" className="text-blue-500 hover:underline">
            Home
          </Link>
          {user ? (
            <Link to="/logout" className="text-blue-500 hover:underline">
              Logout
            </Link>
          ) : (
            <Link to="/register" className="text-blue-500 hover:underline">
              Register
            </Link>
          )}
        </div>
      </header>

      <main className="m-8">
        <Outlet />
      </main>
    </>
  );
}
