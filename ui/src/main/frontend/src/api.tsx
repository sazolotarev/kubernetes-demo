import Cookies from "js-cookie";

export function apiFetch(
  input: RequestInfo | URL,
  init?: RequestInit,
): Promise<Response> {
  const apiInit = init || {};
  if (
    apiInit.method &&
    ["POST", "PUT", "PATCH", "DELETE"].includes(apiInit.method.toUpperCase())
  ) {
    const xsrfToken = Cookies.get("XSRF-TOKEN");
    if (xsrfToken) {
      apiInit.headers = {
        ...apiInit.headers,
        "X-XSRF-TOKEN": xsrfToken,
      };
    }
  }
  apiInit.credentials = "include";

  return fetch(input, apiInit);
}
