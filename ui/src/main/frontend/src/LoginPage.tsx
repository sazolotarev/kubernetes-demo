import React, { useRef, useState } from "react";
import { useNavigate } from "react-router";
import { apiFetch } from "./api";
import Spinner from "./Spinner";

export default function LoginPage() {
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
      const params = new URLSearchParams();
      params.append("username", usernameRef.current?.value || "");
      params.append("password", passwordRef.current?.value || "");

      const response = await apiFetch("/api/login", {
        method: "POST",
        body: params,
      });
      if (!response.ok) {
        if (response.status === 401) {
          throw new Error("Invalid username and/or password");
        }
        const data = await response.json();
        throw new Error(data.detail);
      }
      navigate("/");
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
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
            Login
          </button>

          {isLoading && (
            <div className="ml-2">
              <Spinner />
            </div>
          )}
        </div>
      </form>
    </>
  );
}
