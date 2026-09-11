import { useEffect } from "react";
import { useNavigate } from "react-router";
import { apiFetch } from "./api";
import Spinner from "./Spinner";

export default function LogoutPage() {
  const navigate = useNavigate();

  useEffect(() => {
    logout();
  }, []);

  async function logout() {
    try {
      const response = await apiFetch("/api/logout", {
        method: "POST",
      });
      if (!response.ok) {
        const data = await response.json();
        throw new Error(data.detail);
      }
      navigate("/login");
    } catch (e: any) {
      console.error("Logout failed", e);
    }
  }

  return (
    <div className="flex">
      Logging out...
      <div className="ml-2">
        <Spinner />
      </div>
    </div>
  );
}
