import React, { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { TrashIcon } from "@heroicons/react/24/outline";
import { apiFetch } from "./api";
import Spinner from "./Spinner";

type TodoList = {
  id: string;
  name: string;
};

type TodoItem = {
  id: string;
  description: string;
};

type CreateTodoListFormProps = {
  todoListId: string;
  onSuccess: () => void;
};

export default function TodoListDetailsPage() {
  const [todoList, setTodoList] = useState<TodoList>();
  const [error, setError] = useState<string>("");
  const [isLoading, setLoading] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    loadTodoList();
  }, []);

  async function loadTodoList() {
    setError("");
    setLoading(true);

    try {
      const response = await apiFetch(
        "/api/todo/lists/" + encodeURIComponent(id!),
      );
      if (response.status === 401) {
        navigate("/login");
        return;
      }
      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.detail);
      }
      setTodoList(data);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
      <h1 className="mb-4 text-3xl">
        {isLoading && (
          <div>
            <Spinner />
          </div>
        )}
        {todoList?.name || "Loading..."}
      </h1>
      {error && <div className="my-2 text-red-500">{error}</div>}

      {!isLoading && !error && <ManageTodoItemsView />}
    </>
  );
}

function ManageTodoItemsView() {
  const [todoItems, setTodoItems] = useState<TodoItem[]>([]);
  const [error, setError] = useState<string>("");
  const [isLoading, setLoading] = useState(false);

  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    loadTodoItems();
  }, []);

  function handleDelete(id: string) {
    return async (event: React.MouseEvent) => {
      event.preventDefault();

      try {
        const response = await apiFetch(
          "/api/todo/items/" + encodeURIComponent(id),
          { method: "DELETE" },
        );
        if (!response.ok) {
          const data = await response.json();
          throw new Error(data.detail);
        }
        loadTodoItems();
      } catch (e: any) {
        alert(e.message);
      }
    };
  }

  function handleCreateSuccess() {
    loadTodoItems();
  }

  async function loadTodoItems() {
    setError("");
    setLoading(true);

    try {
      const response = await apiFetch(
        "/api/todo/items?todoListId=" + encodeURIComponent(id!),
      );
      if (response.status === 401) {
        navigate("/login");
        return;
      }
      const data = await response.json();
      if (!response.ok) {
        throw new Error(data.detail);
      }
      setTodoItems(data.todoItems);
    } catch (e: any) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <>
      {isLoading && (
        <div>
          <Spinner />
        </div>
      )}

      {todoItems.length > 0 && !isLoading && (
        <ul className="flex flex-col">
          {todoItems.map((todoItem) => {
            return (
              <div key={todoItem.id}>
                <div className="my-1 flex items-center">
                  <span>&bull;</span>
                  <span className="ml-2 inline-block">
                    {todoItem.description}
                  </span>
                  <button
                    type="button"
                    className="ml-2 w-5 h-5 shrink-0 text-gray-500 hover:opacity-80"
                    onClick={handleDelete(todoItem.id)}
                  >
                    <TrashIcon />
                  </button>
                </div>
              </div>
            );
          })}
        </ul>
      )}

      {todoItems.length === 0 && !isLoading && !error && (
        <div className="mt-4">
          This To-Do list is empty. Use the form below to create a new item!
        </div>
      )}
      {todoItems.length > 0 && !isLoading && !error && (
        <div className="mt-4">Create a new item:</div>
      )}

      {!error && (
        <div className="mt-2">
          <CreateTodoItemForm
            todoListId={id!}
            onSuccess={handleCreateSuccess}
          />
        </div>
      )}

      {error && <div className="my-2 text-red-500">{error}</div>}
    </>
  );
}

function CreateTodoItemForm(props: Readonly<CreateTodoListFormProps>) {
  const descriptionInputRef = useRef<HTMLInputElement>(null);
  const [error, setError] = useState("");
  const [isSubmitting, setSubmitting] = useState(false);

  async function handleSubmit(event: React.SubmitEvent) {
    event.preventDefault();

    setError("");
    setSubmitting(true);

    try {
      const response = await apiFetch("/api/todo/items", {
        method: "POST",
        body: JSON.stringify({
          todoListId: props.todoListId,
          description: descriptionInputRef.current?.value || "",
        }),
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (!response.ok) {
        const data = await response.json();
        throw new Error(data.detail);
      }
      descriptionInputRef.current!.value = "";
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
            ref={descriptionInputRef}
            type="text"
            name="name"
            placeholder="Description"
            className="w-full max-w-80 px-4 py-2 border border-gray-300 rounded-sm"
            required
          />
        </div>
        <div className="mt-2">
          <button
            type="submit"
            className="px-4 py-2 text-white bg-blue-500 hover:bg-blue-600 rounded-sm"
          >
            Create Item
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
