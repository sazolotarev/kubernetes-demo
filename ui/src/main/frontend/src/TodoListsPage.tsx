import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router";
import { TrashIcon } from "@heroicons/react/24/outline";
import { apiFetch } from "./api";
import Spinner from "./Spinner";

type TodoList = {
  id: string;
  name: string;
};

type CreateTodoListFormProps = {
  onSuccess: () => void;
};

export default function TodoListsPage() {
  const [todoLists, setTodoLists] = useState<TodoList[]>([]);
  const [error, setError] = useState<string>("");
  const [isLoading, setLoading] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    loadTodoLists();
  }, []);

  function handleDelete(id: string) {
    return async (event: React.MouseEvent) => {
      event.preventDefault();

      try {
        const response = await apiFetch(
          "/api/todo/lists/" + encodeURIComponent(id),
          { method: "DELETE" },
        );
        if (!response.ok) {
          const data = await response.json();
          throw new Error(data.detail);
        }
        loadTodoLists();
      } catch (e: any) {
        alert(e.message);
      }
    };
  }

  function handleCreateSuccess() {
    loadTodoLists();
  }

  async function loadTodoLists() {
    setError("");
    setLoading(true);

    try {
      const response = await fetch("/api/todo/lists");
      if (response.status === 401) {
        navigate("/login");
        return;
      }
      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.detail);
      }
      setTodoLists(data.todoLists);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
      <h1 className="mb-4 text-3xl">Your To-Do lists</h1>

      {isLoading && (
        <div>
          <Spinner />
        </div>
      )}

      {todoLists.length > 0 && !isLoading && (
        <ul className="flex flex-col">
          {todoLists.map((todoList) => {
            return (
              <div key={todoList.id}>
                <div className="my-1 flex items-center">
                  <span>&bull;</span>
                  <span className="ml-2 inline-block text-blue-500 hover:underline">
                    <Link to={"/todo/lists/" + encodeURIComponent(todoList.id)}>
                      {todoList.name}
                    </Link>
                  </span>
                  <button
                    type="button"
                    className="ml-2 w-5 h-5 shrink-0 text-gray-500 hover:opacity-80"
                    onClick={handleDelete(todoList.id)}
                  >
                    <TrashIcon />
                  </button>
                </div>
              </div>
            );
          })}
        </ul>
      )}

      {todoLists.length === 0 && !isLoading && !error && (
        <div className="mt-4">
          You don't have any To-Do list yet. Use the form below to create your
          first list!
        </div>
      )}
      {todoLists.length > 0 && !isLoading && !error && (
        <div className="mt-4">Create another list:</div>
      )}

      {!error && (
        <div className="mt-2">
          <CreateTodoListForm onSuccess={handleCreateSuccess} />
        </div>
      )}

      {error && <div className="my-2 text-red-500">{error}</div>}
    </>
  );
}

function CreateTodoListForm(props: Readonly<CreateTodoListFormProps>) {
  const nameInputRef = useRef<HTMLInputElement>(null);
  const [error, setError] = useState("");
  const [isSubmitting, setSubmitting] = useState(false);

  async function handleSubmit(event: React.SubmitEvent) {
    event.preventDefault();

    setError("");
    setSubmitting(true);

    try {
      const response = await apiFetch("/api/todo/lists", {
        method: "POST",
        body: JSON.stringify({
          name: nameInputRef.current?.value || "",
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        const data = await response.json();
        throw new Error(data.detail);
      }
      nameInputRef.current!.value = "";
      props.onSuccess();
    } catch (e: any) {
      setError(e.message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <div className="mt-2">
        <div>
          <input
            ref={nameInputRef}
            type="text"
            name="name"
            placeholder="e.g. My new list"
            className="w-full max-w-80 px-4 py-2 border border-gray-300 rounded-sm"
            required
          />
        </div>
        <div className="mt-2">
          <button
            type="submit"
            className="px-4 py-2 text-white bg-blue-500 hover:bg-blue-600 rounded-sm"
          >
            Create List
          </button>
        </div>
      </div>
      {isSubmitting && (
        <div className="mt-2">
          <Spinner />
        </div>
      )}
      {error && <div className="mt-2 text-red-500">{error}</div>}
    </form>
  );
}
