import React, { useRef, useState } from "react";
import { useNavigate } from "react-router";
import { apiFetch } from "./api";
import Spinner from "./Spinner";

export default function RegisterPage() {
  const usernameRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const [error, setError] = useState("");
  const [isLoading, setLoading] = useState(false);

  const navigate = useNavigate();

  async function handleSubmit(event: React.SubmitEvent) {
    event.preventDefault();

    setError("");
    setLoading(true);

    try {
      const response = await apiFetch("/api/users", {
        method: "POST",
        body: JSON.stringify({
          username: usernameRef.current?.value || "",
          password: passwordRef.current?.value || "",
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        const data = await response.json();
        throw new Error(data.detail);
      }
      alert("Success! You can now log in with your username and password.");
      navigate("/login");
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <div className="mt-4">Username:</div>
      <div>
        <input
          ref={usernameRef}
          type="text"
          autoCorrect="off"
          className="w-full max-w-80 mt-2 px-4 py-2 border border-gray-300 rounded-sm"
          required
          minLength={2}
        />
      </div>

      <div className="mt-4">Password:</div>
      <div>
        <input
          ref={passwordRef}
          type="password"
          autoCorrect="off"
          className="w-full max-w-80 mt-2 px-4 py-2 border border-gray-300 rounded-sm"
          required
          minLength={6}
        />
      </div>

      {!!error && (
        <div className="w-full max-w-80 mt-4">
          <span className="text-wrap text-red-500">{error}</span>
        </div>
      )}

      <div className="flex flex-row items-center mt-8">
        <button
          type="submit"
          className="px-4 py-2 text-white bg-blue-500 hover:bg-blue-600 rounded-sm"
        >
          Register
        </button>
        {isLoading && (
          <div className="ml-2">
            <Spinner />
          </div>
        )}
      </div>
    </form>
  );
}
